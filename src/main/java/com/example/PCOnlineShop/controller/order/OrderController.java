package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.payment.PaymentInfoDTO;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.payment.Payment; // ✅ Import
import com.example.PCOnlineShop.service.address.AddressService;
import com.example.PCOnlineShop.service.cart.CartService;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.payment.PaymentService; // ✅ Import
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

    // (Bỏ PAGE_SIZE nếu không dùng)
    // private final int PAGE_SIZE = 10;
    private final OrderService orderService;
    private final CartService cartService;
    private final AccountRepository accountRepository;
    private final AddressService addressService;
    private final PaymentService paymentService; // ✅ Tiêm PaymentService

    // ✅ Cập nhật constructor để tiêm PaymentService
    public OrderController(OrderService orderService, AccountRepository accountRepository,
                           CartService cartService, AddressService addressService,
                           PaymentService paymentService) { // ✅
        this.addressService = addressService;
        this.orderService = orderService;
        this.accountRepository = accountRepository;
        this.cartService = cartService;
        this.paymentService = paymentService; // ✅
    }

    // --- Lấy Account (Tách hàm helper) ---
    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        if (userDetails instanceof Account) {
            return (Account) userDetails;
        }
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    // ======================================================
    @GetMapping("/list")
    public String viewOrderList(Model model,
                                @AuthenticationPrincipal UserDetails currentUserDetails) {

        // 1. Xác định vai trò (Giữ nguyên)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        // Lấy Account (Giữ nguyên)
        Account currentAccount = getCurrentAccount(currentUserDetails);

        // 2. Xử lý theo vai trò
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

        return "orders/order-list"; // Trả về view chung
    }

    // ======================================================
    //  Hiển thị Chi tiết đơn hàng
    // ======================================================
    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable long id, Model model, // ✅ Đổi sang long
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes) {

        // 1. Lấy thông tin người dùng và vai trò
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        Account currentAccount = getCurrentAccount(currentUserDetails);

        // 2. Lấy thông tin đơn hàng (dùng long)
        Order order = orderService.getOrderById(id);
        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
            return "redirect:/orders/list";
        }

        // 3. KIỂM TRA QUYỀN TRUY CẬP CHO CUSTOMER
        if (!isStaffOrAdmin) {
            if (currentAccount == null || order.getAccount() == null || order.getAccount().getAccountId() != currentAccount.getAccountId()) {
                redirectAttributes.addFlashAttribute("error", "You are not authorized to view this order.");
                return "redirect:/orders/list";
            }
        }

        // 4. Lấy chi tiết sản phẩm và gửi sang view (dùng long)
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));
        model.addAttribute("pageTitle", "Order Detail #" + order.getOrderId());

        // 5.  MỚI: LẤY THÔNG TIN THANH TOÁN
        try {
            PaymentInfoDTO paymentInfo = paymentService.getPaymentInfoByOrderId(id);
            model.addAttribute("paymentInfo", paymentInfo);
        } catch (EntityNotFoundException e) {
            // Không tìm thấy payment (ví dụ: đơn hàng cũ), không làm gì cả
            model.addAttribute("paymentInfo", null);
        }
        return "orders/order-detail";
    }

    /**
     * MỚI: Endpoint cho Staff: Processing -> Ready to Ship
     */
    @PostMapping("/mark-ready-to-ship")
    @ResponseBody
    public ResponseEntity<?> markReadyToShip(@RequestParam("orderId") long orderId) {
        try {
            orderService.markAsReadyToShip(orderId);
            return ResponseEntity.ok(Map.of("message", "Đã cập nhật sang Ready to Ship"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * MỚI: Endpoint cho Staff: Processing -> Cancelled (Refund)
     */
    @PostMapping("/staff-cancel")
    @ResponseBody
    public ResponseEntity<?> staffCancelOrder(@RequestParam("orderId") long orderId) {
        try {
            orderService.refundOrderFromOrderId(orderId);
            return ResponseEntity.ok(Map.of("message", "Đơn hàng đã được hủy. Hoàn tiền đang chờ xử lý."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ======================================================
    // == HIỂN THỊ TRANG CHECKOUT (GET)
    // ======================================================
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model,
                                   @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        List<CartItemDTO> cartItems = cartService.getCartItems(currentAccount);
        if (cartItems.isEmpty()) return "redirect:/cart";

        double grandTotal = cartService.calculateGrandTotal(cartItems);

        // --- Logic lấy địa chỉ ---
        List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);
        Optional<Address> defaultAddressOpt = addressService.getDefaultAddress(currentAccount);

        Address defaultAddress = defaultAddressOpt.orElse(
                allAddresses.isEmpty() ? null : allAddresses.get(0)
        );

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

        return "orders/checkout";
    }

    @GetMapping("/build-checkout")
    public String showBuildCheckoutPage(Model model,
                                   @SessionAttribute("cartBuilds") List<CartItemDTO> cartBuilds,
                                   @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        List<CartItemDTO> cartItems = cartBuilds;
        if (cartItems.isEmpty()) return "redirect:/cart";

        double grandTotal = cartService.calculateGrandTotal(cartItems);

        // --- Logic lấy địa chỉ ---
        List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);
        Optional<Address> defaultAddressOpt = addressService.getDefaultAddress(currentAccount);

        Address defaultAddress = defaultAddressOpt.orElse(
                allAddresses.isEmpty() ? null : allAddresses.get(0)
        );

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

        return "orders/checkout";
    }

    // ======================================================
    // == PHẦN MỚI: XỬ LÝ ĐẶT HÀNG (POST) VÀ CHUYỂN SANG PAYOS
    // ======================================================
    @PostMapping("/checkout")
    public String processCheckout(@Valid @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        // --- Validate thủ công ---
        if ("Giao hàng tận nơi".equals(checkoutDTO.getShippingMethod())) {
            if (checkoutDTO.getShippingFullName() == null || checkoutDTO.getShippingFullName().isBlank()) {
                bindingResult.rejectValue("shippingFullName", "NotBlank", "Họ tên không được để trống");
            }
            if (checkoutDTO.getShippingPhone() == null || checkoutDTO.getShippingPhone().isBlank()) {
                bindingResult.rejectValue("shippingPhone", "NotBlank", "Số điện thoại không được để trống");
            }
            if (checkoutDTO.getShippingAddress() == null || checkoutDTO.getShippingAddress().isBlank()) {
                bindingResult.rejectValue("shippingAddress", "NotBlank", "Địa chỉ không được để trống");
            }
        } else {
            // Nếu "Nhận tại cửa hàng", set giá trị mặc định để qua mặt DB
            checkoutDTO.setShippingFullName("Store Pickup");
            checkoutDTO.setShippingPhone("000000000");
            checkoutDTO.setShippingAddress("Nhận tại cửa hàng");
        }


        if (bindingResult.hasErrors()) {
            // (Trả lại toàn bộ thông tin giỏ hàng/địa chỉ nếu lỗi)
            List<CartItemDTO> cartItems = cartService.getCartItems(currentAccount);
            double grandTotal = cartService.calculateGrandTotal(cartItems);
            List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("grandTotal", grandTotal);
            model.addAttribute("account", currentAccount);
            model.addAttribute("allAddresses", allAddresses);
            Address defaultAddress = addressService.getDefaultAddress(currentAccount).orElse(
                    allAddresses.isEmpty() ? null : allAddresses.get(0)
            );
            model.addAttribute("defaultAddress", defaultAddress);

            return "orders/checkout";
        }

        Map<Integer, Integer> cartMap = cartService.getCartMapForCheckout(currentAccount);
        if (cartMap.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn bị trống!");
            return "redirect:/cart";
        }

        Order newOrder = null;
        try {
            // BƯỚC 1: TẠO ORDER (Status: "Pending Payment")
            newOrder = orderService.createOrder(
                    currentAccount,
                    cartMap,
                    checkoutDTO.getShippingMethod(),
                    checkoutDTO.getNote(),
                    checkoutDTO.getShippingFullName(),
                    checkoutDTO.getShippingPhone(),
                    checkoutDTO.getShippingAddress()
            );

            // BƯỚC 2: TẠO BẢN GHI THANH TOÁN (Status: "PENDING")
            //
            Payment newPayment = paymentService.createPaymentRecord(newOrder);

            // BƯỚC 3: TẠO LINK PAYOS TỪ BẢN GHI THANH TOÁN
            //
            String payosCheckoutUrl = paymentService.createPayOSLink(newPayment);

            // BƯỚC 4: XÓA GIỎ HÀNG
            cartService.clearCart(currentAccount);

            // BƯỚC 5: CHUYỂN HƯỚNG SANG TRANG THANH TOÁN PAYOS
            return "redirect:" + payosCheckoutUrl;

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để debug
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi tạo thanh toán: " + e.getMessage());

            // Nếu đã lỡ tạo đơn hàng nhưng lỗi PayOS, chuyển về list
            if (newOrder != null) {
                return "redirect:/orders/list";
            }
            // Nếu lỗi ngay cả khi tạo đơn, về lại checkout
            return "redirect:/checkout";
        }
    }
}