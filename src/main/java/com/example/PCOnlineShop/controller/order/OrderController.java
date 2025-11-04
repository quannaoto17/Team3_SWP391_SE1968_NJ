package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.dto.order.CheckoutDTO;
import com.example.PCOnlineShop.dto.order.OrderSearchRequest;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.address.AddressService;
import com.example.PCOnlineShop.service.cart.CartService;
import com.example.PCOnlineShop.service.order.OrderService;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final int PAGE_SIZE = 10;
    private final CartService cartService;
    private final AccountRepository accountRepository;
    private final AddressService addressService;


    public OrderController(OrderService orderService, AccountRepository accountRepository,
                           CartService cartService, AddressService addressService) {
        this.addressService = addressService;
        this.orderService = orderService;
        this.accountRepository = accountRepository;
        this.cartService = cartService;
    }

    // --- Lấy Account (Tách hàm helper) ---
    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        if (userDetails instanceof Account) {
            return (Account) userDetails;
        }
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    // Trong OrderController.java

    // ======================================================
    @GetMapping("/list")
    public String viewOrderList(Model model,
                                @AuthenticationPrincipal UserDetails currentUserDetails
                                // --- BỎ CÁC THAM SỐ PAGE, SORT, DIR, VALIDATION ---
                                // @Valid OrderSearchRequest searchRequest,
                                // BindingResult bindingResult,
                                // @RequestParam(name = "page", ...
                                // @RequestParam(name = "sort", ...
                                // @RequestParam(name = "dir", ...
    ) {

        // 1. Xác định vai trò (Giữ nguyên)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        // Lấy Account (Giữ nguyên)
        Account currentAccount = null;
        if (currentUserDetails instanceof Account) {
            currentAccount = (Account) currentUserDetails;
        } else if (currentUserDetails != null) {
            String username = currentUserDetails.getUsername();
            currentAccount = accountRepository.findByPhoneNumber(username).orElse(null);
            if (currentAccount == null) {
                System.err.println("Cảnh báo: Không tìm thấy Account với SĐT (username): " + username);
            }
        }

        // 2. Xử lý theo vai trò
        if (isStaffOrAdmin) {
            // --- LOGIC MỚI CHO STAFF/ADMIN (DÙNG DATATABLES) ---
            model.addAttribute("pageTitle", "Order Management");

            // Lấy TẤT CẢ đơn hàng thay vì phân trang
            List<Order> adminOrderList = orderService.findAllOrdersForAdmin();

            // Gửi List này sang view
            model.addAttribute("adminOrderList", adminOrderList);

            // --- BỎ TOÀN BỘ LOGIC PAGEABLE, SORT, SEARCH, ERROR CŨ ---

        } else if (currentAccount != null) {
            // --- XỬ LÝ CHO CUSTOMER (Giữ nguyên) ---
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
    public String viewOrderDetail(@PathVariable int id, Model model,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes) {

        // 1. Lấy thông tin người dùng và vai trò
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        Account currentAccount = null;
        if (currentUserDetails instanceof Account) {
            currentAccount = (Account) currentUserDetails;
        } else if (currentUserDetails != null) {
            // Fallback: Lấy Account từ DB
            String username = currentUserDetails.getUsername();
            // Sử dụng accountRepository để tìm
            currentAccount = accountRepository.findByPhoneNumber(username).orElse(null);
            if (currentAccount == null) {
                System.err.println("Cảnh báo: Không tìm thấy Account với SĐT (username): " + username + " khi xem chi tiết.");
            }
        }

        // 2. Lấy thông tin đơn hàng
        Order order = orderService.getOrderById(id);
        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Order not found.");
            return isStaffOrAdmin ? "redirect:/orders/list?page=1" : "redirect:/orders/list"; // Quay về list tương ứng
        }

        // 3. KIỂM TRA QUYỀN TRUY CẬP CHO CUSTOMER
        if (!isStaffOrAdmin) {
            if (currentAccount == null || order.getAccount() == null || order.getAccount().getAccountId() != currentAccount.getAccountId()) {
                redirectAttributes.addFlashAttribute("error", "You are not authorized to view this order.");
                return "redirect:/orders/list"; // Customer chỉ về list của họ
            }
        }

        // 4. Lấy chi tiết sản phẩm và gửi sang view
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));
        model.addAttribute("pageTitle", "Order Detail #" + order.getOrderId()); // Tiêu đề trang

        return "orders/order-detail"; // Trả về view chung
    }

    // === THÊM ENDPOINT MỚI CHO AJAX "SAVE ON CHANGE" ===
    @PostMapping("/update-status")
    @ResponseBody // Rất quan trọng: Báo Spring trả về JSON/Text, không phải tên View
    public ResponseEntity<?> updateSingleOrderStatus(@RequestParam("orderId") int orderId,
                                                     @RequestParam("newStatus") String newStatus) {
        try {
            // Gọi service mới
            orderService.updateSingleOrderStatus(orderId, newStatus);

            // Trả về 200 OK với một thông báo
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));

        } catch (EntityNotFoundException e) {
            // Nếu không tìm thấy Order ID
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Các lỗi khác (lỗi server, v.v.)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
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

        // Nếu không có địa chỉ mặc định, lấy cái đầu tiên (nếu có)
        Address defaultAddress = defaultAddressOpt.orElse(
                allAddresses.isEmpty() ? null : allAddresses.get(0)
        );

        CheckoutDTO checkoutDTO = new CheckoutDTO();
        checkoutDTO.setShippingMethod("Giao hàng tận nơi"); // Mặc định

        if (defaultAddress != null) {
            checkoutDTO.setShippingFullName(defaultAddress.getFullName());
            checkoutDTO.setShippingPhone(defaultAddress.getPhone());
            checkoutDTO.setShippingAddress(defaultAddress.getAddress());
        }

        // Gửi thông tin sang view
        model.addAttribute("checkoutDTO", checkoutDTO);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("grandTotal", grandTotal);
        model.addAttribute("account", currentAccount); // Gửi thông tin người đặt hàng
        model.addAttribute("defaultAddress", defaultAddress); // Địa chỉ mặc định để hiển thị
        model.addAttribute("allAddresses", allAddresses); // Gửi DANH SÁCH địa chỉ cho pop-up
        model.addAttribute("pageTitle", "Checkout");

        return "orders/checkout"; // Trả về file checkout
    }

    // ======================================================
    // == PHẦN MỚI: XỬ LÝ ĐẶT HÀNG (POST)
    // ======================================================
    @PostMapping("/checkout")
    public String processCheckout(@Valid @ModelAttribute("checkoutDTO") CheckoutDTO checkoutDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal UserDetails currentUserDetails,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        Account currentAccount = getCurrentAccount(currentUserDetails);
        if (currentAccount == null) return "redirect:/auth/login";

        // --- (Logic validate của bạn) ---
        if ("Giao hàng tận nơi".equals(checkoutDTO.getShippingMethod())) {
            // ... (validation)
        } else {
            // ... (xử lý nhận tại cửa hàng)
        }


        if (bindingResult.hasErrors()) {
            // ... (xử lý trả lại trang checkout nếu lỗi)
            return "orders/checkout";
        }

        Map<Integer, Integer> cartMap = cartService.getCartMapForCheckout(currentAccount);
        if (cartMap.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn bị trống!");
            return "redirect:/cart";
        }

        try {
            // ✅ BƯỚC 1: GỌI SERVICE ĐỂ TẠO ORDER
            Order newOrder = orderService.createOrder(
                    currentAccount,
                    cartMap,
                    checkoutDTO.getShippingMethod(),
                    checkoutDTO.getNote(),
                    checkoutDTO.getShippingFullName(),
                    checkoutDTO.getShippingPhone(),
                    checkoutDTO.getShippingAddress()
            );

            // ✅ BƯỚC 2: SAU KHI TẠO XONG, XÓA GIỎ HÀNG VÀ CHUYỂN HƯỚNG
            cartService.clearCart(currentAccount);
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Mã đơn hàng của bạn là #" + newOrder.getOrderId());

            // ✅ BƯỚC 3: ĐƠN HÀNG XUẤT HIỆN Ở ORDER-LIST
            return "redirect:/orders/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

}