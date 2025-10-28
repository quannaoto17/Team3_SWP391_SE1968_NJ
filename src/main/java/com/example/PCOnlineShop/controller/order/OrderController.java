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
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // ======================================================
    @GetMapping("/list")
    public String viewOrderList(Model model,
                                @AuthenticationPrincipal UserDetails currentUserDetails,
                                @Valid OrderSearchRequest searchRequest,
                                BindingResult bindingResult,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(name = "sort", required = false, defaultValue = "createdDate") String sortField,
                                @RequestParam(name = "dir", required = false, defaultValue = "desc") String sortDir,
                                RedirectAttributes redirectAttributes) {

        // 1. Xác định vai trò và lấy thông tin người dùng
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        Account currentAccount = null;
        if (currentUserDetails instanceof Account) {
            currentAccount = (Account) currentUserDetails;
        } else if (currentUserDetails != null) {
            // Fallback: Lấy Account từ DB bằng username (coi như là SĐT)
            String username = currentUserDetails.getUsername();
            // Sử dụng accountRepository để tìm
            currentAccount = accountRepository.findByPhoneNumber(username).orElse(null);

            if (currentAccount == null) {
                System.err.println("Cảnh báo: Không tìm thấy Account với SĐT (username): " + username);
            }
        }

        // 2. Xử lý theo vai trò
        if (isStaffOrAdmin) {
            // --- XỬ LÝ CHO STAFF/ADMIN ---
            model.addAttribute("pageTitle", "Order Management");

            String searchPhone = searchRequest.getSearchPhone();
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();
            Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);
            String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";


            // --- Xử lý lỗi validation DTO (sai format) ---
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                model.addAttribute("error", errorMessage);

                // Cung cấp model đầy đủ để trang không bị crash
                model.addAttribute("ordersPage", Page.empty(pageable)); // Gửi trang rỗng
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", 0);
                model.addAttribute("sortField", sortField);
                model.addAttribute("sortDir", sortDir);
                model.addAttribute("reverseSortDir", reverseSortDir);
                model.addAttribute("searchPhone", searchPhone); // Giữ lại SĐT sai

                return "orders/order-list";
            }

            // --- Xử lý SĐT không tồn tại ---
            if (StringUtils.hasText(searchPhone)) {
                if (!orderService.customerAccountExistsByPhoneNumber(searchPhone)) {
                    model.addAttribute("error", "No customer found with phone number: " + searchPhone);

                    // Cung cấp model đầy đủ để trang không bị crash
                    model.addAttribute("ordersPage", Page.empty(pageable)); // Gửi trang rỗng
                    model.addAttribute("currentPage", page);
                    model.addAttribute("totalPages", 0);
                    model.addAttribute("sortField", sortField);
                    model.addAttribute("sortDir", sortDir);
                    model.addAttribute("reverseSortDir", reverseSortDir);
                    model.addAttribute("searchPhone", searchPhone); // Giữ lại SĐT sai

                    return "orders/order-list";
                }
            }

            // --- Logic thành công ---
            Page<Order> orderPage = orderService.findPaginated(pageable, searchPhone);

            // Gửi dữ liệu Staff sang view
            model.addAttribute("ordersPage", orderPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orderPage.getTotalPages());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", reverseSortDir);
            model.addAttribute("searchPhone", searchPhone);

        } else if (currentAccount != null) {
            // --- XỬ LÝ CHO CUSTOMER ---
            // (Code sẽ vào đây khi currentAccount được tìm thấy)
            model.addAttribute("pageTitle", "My Orders"); // Tiêu đề trang
            List<Order> customerOrders = orderService.getOrdersByAccount(currentAccount);
            model.addAttribute("customerOrders", customerOrders); // Đặt tên khác

        } else {
            // Chưa đăng nhập hoặc không lấy được Account -> Chuyển về login (Security nên xử lý trước)
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

    // =============================================================
    // Cập nhật hàng loạt trạng thái (POST /update-all-status) - Chỉ cho Staff/Admin
    // =============================================================
    @PostMapping("/update-all-status")
    public String updateAllOrderStatuses(
            @RequestParam Map<String, String> statusUpdates,
            RedirectAttributes redirectAttributes) {

        Map<Integer, String> parsedUpdates = new HashMap<>();
        for (Map.Entry<String, String> entry : statusUpdates.entrySet()) {
            if (entry.getKey().startsWith("statusUpdates[") && entry.getKey().endsWith("]")) {
                try {
                    String key = entry.getKey();
                    int orderId = Integer.parseInt(key.substring(key.indexOf('[') + 1, key.indexOf(']')));
                    String newStatus = entry.getValue();
                    parsedUpdates.put(orderId, newStatus);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    System.err.println("Lỗi phân tích key cập nhật trạng thái: " + entry.getKey());
                }
            }
        }

        // --- Gọi service và xử lý kết quả giữ nguyên như cũ ---
        try {
            if (!parsedUpdates.isEmpty()) {
                orderService.updateMultipleOrderStatuses(parsedUpdates);
                redirectAttributes.addFlashAttribute("success", "Order statuses updated successfully.");
            } else {
                redirectAttributes.addFlashAttribute("info", "No status changes were submitted.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating statuses: " + e.getMessage());
        }


        return "redirect:/orders/list"; // Quay về list chung
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

        // --- Validate thủ công ---
        // Chỉ validate địa chỉ nếu chọn "Giao hàng tận nơi"
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
            // Nếu lỗi, phải gửi lại toàn bộ thông tin
            List<CartItemDTO> cartItems = cartService.getCartItems(currentAccount);
            double grandTotal = cartService.calculateGrandTotal(cartItems);
            List<Address> allAddresses = addressService.getAddressesForAccount(currentAccount);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("grandTotal", grandTotal);
            model.addAttribute("account", currentAccount);
            model.addAttribute("allAddresses", allAddresses);

            // Tìm lại default address để hiển thị
            Address defaultAddress = addressService.getDefaultAddress(currentAccount).orElse(
                    allAddresses.isEmpty() ? null : allAddresses.get(0)
            );
            model.addAttribute("defaultAddress", defaultAddress);

            return "orders/checkout";
        }

        // Lấy Map giỏ hàng để tạo đơn
        Map<Integer, Integer> cartMap = cartService.getCartMapForCheckout(currentAccount);
        if (cartMap.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn bị trống!");
            return "redirect:/cart";
        }

        try {
            Order newOrder = orderService.createOrder(
                    currentAccount,
                    cartMap,
                    checkoutDTO.getShippingMethod(),
                    checkoutDTO.getNote(),
                    checkoutDTO.getShippingFullName(),
                    checkoutDTO.getShippingPhone(),
                    checkoutDTO.getShippingAddress()
            );

            cartService.clearCart(currentAccount);
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công! Mã đơn hàng của bạn là #" + newOrder.getOrderId());
            return "redirect:/orders/list";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

}