package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.model.cart.CartItem; // ✅ THÊM
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.product.ProductRepository; // ✅ THÊM
import com.example.PCOnlineShop.service.address.AddressService;
import com.example.PCOnlineShop.service.cart.CartService;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.payment.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final AccountRepository accountRepository;
    private final AddressService addressService;
    private final PaymentService paymentService;


    public OrderController(OrderService orderService, AccountRepository accountRepository,
                           CartService cartService, AddressService addressService,
                           PaymentService paymentService
) {
        this.addressService = addressService;
        this.orderService = orderService;
        this.accountRepository = accountRepository;
        this.cartService = cartService;
        this.paymentService = paymentService;
;
    }

    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        if (userDetails instanceof Account) return (Account) userDetails;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    @GetMapping("/list")
    public String viewOrderList(Model model, @AuthenticationPrincipal UserDetails currentUserDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);
        Account currentAccount = getCurrentAccount(currentUserDetails);

        if (isStaffOrAdmin) {
            model.addAttribute("pageTitle", "Order Management");
            List<Order> adminOrderList = orderService.findAllOrdersForAdmin();
            model.addAttribute("adminOrderList", adminOrderList);
        } else if (currentAccount != null) {
            model.addAttribute("pageTitle", "My Orders");
            List<Order> customerOrders = orderService.getOrdersByAccount(currentAccount);
            model.addAttribute("customerOrders", customerOrders);
        } else {
            return "redirect:/auth/login";
        }
        return "orders/order-list";
    }

// ======================================================
// HIỂN THỊ CHI TIẾT ĐƠN HÀNG
// ======================================================

    @GetMapping("/detail/{id}")

    public String viewOrderDetail(@PathVariable long id, Model model,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);
        Account currentAccount = getCurrentAccount(currentUserDetails);
        Order order = orderService.getOrderById(id);

        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
            return "redirect:/orders/list";
        }

        if (!isStaffOrAdmin) {
            if (currentAccount == null || order.getAccount() == null || order.getAccount().getAccountId() != currentAccount.getAccountId()) {
                redirectAttributes.addFlashAttribute("error", "You are not authorized to view this order.");
                return "redirect:/orders/list";
            }
        }



        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));
        model.addAttribute("pageTitle", "Order Detail #" + order.getOrderId());



        try {
            PaymentInfoDTO paymentInfo = paymentService.getPaymentInfoByOrderId(id);
            model.addAttribute("paymentInfo", paymentInfo);
        } catch (EntityNotFoundException e) {
            model.addAttribute("paymentInfo", null);
        }
        return "orders/order-detail";

    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model,
                                   @AuthenticationPrincipal UserDetails currentUserDetails,
                                   RedirectAttributes redirectAttributes) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        List<CartItemDTO> cartItems = cartService.getCartItems(currentAccount)
                .stream()
                .filter(CartItemDTO::isSelected)
                .toList();

        if (cartItems.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No chosen products to checkout.");
            return "redirect:/cart";
        }

        double grandTotal = cartItems.stream().mapToDouble(CartItemDTO::getSubtotal).sum();

        List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);
        Optional<Address> defaultAddressOpt = addressService.getDefaultAddress(currentAccount);
        Address defaultAddress = defaultAddressOpt.orElse(allAddresses.isEmpty() ? null : allAddresses.get(0));
        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setShippingMethod("Giao hàng tận nơi");
        if (defaultAddress != null) {
            checkoutDTO.setShippingFullName(defaultAddress.getFullName());
            checkoutDTO.setShippingPhone(defaultAddress.getPhone());
            checkoutDTO.setShippingAddress(defaultAddress.getAddress());
        }

        model.addAttribute("checkoutDTO", checkoutDTO);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);
        model.addAttribute("account", currentAccount);
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("allAddresses", allAddresses);
        model.addAttribute("pageTitle", "Checkout");
        model.addAttribute("postActionUrl", "/orders/checkout");

        return "orders/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@Valid @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        if (bindingResult.hasErrors()) {

            List<CartItemDTO> cartItems = cartService.getCartItems(currentAccount).stream().filter(CartItemDTO::isSelected).toList();
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("grandTotal", cartItems.stream().mapToDouble(CartItemDTO::getSubtotal).sum());
            model.addAttribute("account", currentAccount);
            List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);
            model.addAttribute("allAddresses", allAddresses);
            Optional<Address> defaultAddressOpt = addressService.getDefaultAddress(currentAccount);
            model.addAttribute("defaultAddress", defaultAddressOpt.orElse(allAddresses.isEmpty() ? null : allAddresses.get(0)));
            model.addAttribute("postActionUrl", "/orders/checkout");
            return "orders/checkout";
        }

        Map<Integer, CartItem> checkoutMap = cartService.getCartMapForCheckout(currentAccount);

        if (checkoutMap.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please choose products!");
            return "redirect:/cart";
        }

        Order newOrder = null;
        try {
            newOrder = orderService.createOrder(
                    currentAccount,
                    checkoutMap,
                    checkoutDTO
            );

            Payment newPayment = paymentService.createPaymentRecord(newOrder);
            String payosCheckoutUrl = paymentService.createPayOSLink(newPayment);

            cartService.clearSelectedItems(currentAccount);

            return "redirect:" + payosCheckoutUrl;

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error while payment: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

}