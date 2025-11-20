package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.order.CheckoutPageDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.payment.Payment;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.cart.CartService;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.service.payment.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final PaymentService paymentService;
    private final AccountRepository accountRepository;

    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    private boolean isStaffOrAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/list")
    public String viewOrderList(Model model, @AuthenticationPrincipal UserDetails currentUserDetails) {
        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        boolean isAdmin = isStaffOrAdmin();
        model.addAttribute("isStaffOrAdmin", isAdmin);
        model.addAttribute("pageTitle", isAdmin ? "Order Management" : "My Orders");

        if (isAdmin) {
            model.addAttribute("adminOrderList", orderService.findAllOrdersForAdmin());
        } else {
            model.addAttribute("customerOrders", orderService.getOrdersByAccount(currentAccount));
        }
        return "orders/order-list";
    }

    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable long id, Model model,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes) {
        Account currentAccount = getCurrentAccount(currentUserDetails);
        boolean isAdmin = isStaffOrAdmin();
        model.addAttribute("isStaffOrAdmin", isAdmin);

        try {
            Order order = orderService.getOrderDetailForView(id, currentAccount, isAdmin);
            model.addAttribute("order", order);
            model.addAttribute("details", orderService.getOrderDetails(id));
            model.addAttribute("pageTitle", "Order Detail #" + order.getOrderId());
            model.addAttribute("paymentInfo", paymentService.getPaymentInfoSafe(id));
            return "orders/order-detail";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/orders/list";
        }
    }

    @GetMapping("/checkout")
    public String showCheckoutPage(Model model,
                                   @AuthenticationPrincipal UserDetails currentUserDetails,
                                   RedirectAttributes redirectAttributes) {
        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        try {
            CheckoutPageDTO data = orderService.prepareCheckoutData(currentAccount);

            model.addAttribute("checkoutDTO", data.getCheckoutDTO());
            model.addAttribute("cartItems", data.getSelectedItems());
            model.addAttribute("grandTotal", data.getGrandTotal());
            model.addAttribute("account", currentAccount);
            model.addAttribute("defaultAddress", data.getDefaultAddress());
            model.addAttribute("allAddresses", data.getAllAddresses());
            model.addAttribute("pageTitle", "Checkout");
            model.addAttribute("postActionUrl", "/orders/checkout");

            return "orders/checkout";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }
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
            try {
                CheckoutPageDTO data = orderService.prepareCheckoutData(currentAccount);
                model.addAttribute("cartItems", data.getSelectedItems());
                model.addAttribute("grandTotal", data.getGrandTotal());
                model.addAttribute("account", currentAccount);
                model.addAttribute("allAddresses", data.getAllAddresses());
                model.addAttribute("defaultAddress", data.getDefaultAddress());
                model.addAttribute("postActionUrl", "/orders/checkout");
                return "orders/checkout";
            } catch (Exception e) {
                return "redirect:/cart";
            }
        }

        try {
            Order newOrder = orderService.processCheckout(currentAccount, checkoutDTO);
            Payment newPayment = paymentService.createPaymentRecord(newOrder);
            String payosCheckoutUrl = paymentService.createPayOSLink(newPayment);
            cartService.clearSelectedItems(currentAccount);
            return "redirect:" + payosCheckoutUrl;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return (e instanceof IllegalStateException) ? "redirect:/cart" : "redirect:/orders/checkout";
        }
    }
}