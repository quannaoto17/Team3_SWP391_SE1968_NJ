package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.constant.RoleName; // Import RoleName
import com.example.PCOnlineShop.dto.order.OrderSearchRequest; // Import DTO tìm kiếm
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication; // Import Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder; // Import SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails; // Import UserDetails
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders") // Base path chung cho cả customer và staff
public class OrderController {

    private final OrderService orderService;
    private final int PAGE_SIZE = 10;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ======================================================
    // 🔹 Hiển thị Danh sách đơn hàng (GET /list) - Gộp 🔹
    // ======================================================
    @GetMapping("/list")
    public String viewOrderList(Model model,
                                // @AuthenticationPrincipal dùng UserDetails để tương thích nhiều kiểu trả về
                                @AuthenticationPrincipal UserDetails currentUserDetails,
                                // Các tham số cho Staff (search, page, sort) - không bắt buộc
                                @Valid OrderSearchRequest searchRequest,
                                BindingResult bindingResult,
                                @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                @RequestParam(name = "sort", required = false, defaultValue = "createdDate") String sortField,
                                @RequestParam(name = "dir", required = false, defaultValue = "desc") String sortDir, // Mặc định mới nhất trước
                                RedirectAttributes redirectAttributes) {

        // 1. Xác định vai trò và lấy thông tin người dùng
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isStaffOrAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STAFF") || r.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isStaffOrAdmin", isStaffOrAdmin);

        Account currentAccount = null;
        if (currentUserDetails instanceof Account) { // Nếu UserDetailsService trả về Account
            currentAccount = (Account) currentUserDetails;
        } else if (currentUserDetails != null) {
            // Nếu UserDetailsService trả về User của Spring, cần lấy Account từ DB
            // currentAccount = accountRepository.findByPhoneNumber(currentUserDetails.getUsername()).orElse(null);
            // TODO: Cần inject AccountRepository và xử lý trường hợp này nếu cần
            System.err.println("Cảnh báo: UserDetails không phải là kiểu Account. Cần xử lý lấy Account từ DB.");
        }


        if (isStaffOrAdmin) {
            // --- XỬ LÝ CHO STAFF/ADMIN ---
            model.addAttribute("pageTitle", "Order Management"); // Tiêu đề trang

            String searchPhone = searchRequest.getSearchPhone();

            // Xử lý lỗi validation DTO (chỉ áp dụng cho Staff/Admin)
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                model.addAttribute("error", errorMessage); // Gửi lỗi trực tiếp, không redirect
                // Vẫn cần lấy dữ liệu trang 1 để hiển thị
                Pageable firstPage = PageRequest.of(0, PAGE_SIZE, Sort.by(sortField).descending());
                model.addAttribute("orders", orderService.findPaginated(firstPage, null)); // Lấy trang đầu
                model.addAttribute("currentPage", 1);
                model.addAttribute("totalPages", 0); // Hoặc tính lại nếu cần
                // ... (thêm các thuộc tính sort/dir mặc định)
                return "orders/order-list";
            }

            // Kiểm tra khách hàng tồn tại (nếu có tìm kiếm)
            if (StringUtils.hasText(searchPhone)) {
                if (!orderService.customerAccountExistsByPhoneNumber(searchPhone)) {
                    model.addAttribute("error", "No customer found with phone number: " + searchPhone);
                    Pageable firstPage = PageRequest.of(0, PAGE_SIZE, Sort.by(sortField).descending());
                    model.addAttribute("orders", orderService.findPaginated(firstPage, null));
                    model.addAttribute("currentPage", 1);
                    model.addAttribute("totalPages", 0);
                    // ... (thêm các thuộc tính sort/dir mặc định)
                    return "orders/order-list";
                }
            }

            // Sắp xếp & Phân trang
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortField).ascending()
                    : Sort.by(sortField).descending();
            Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);
            Page<Order> orderPage = orderService.findPaginated(pageable, searchPhone);

            // Gửi dữ liệu Staff sang view
            model.addAttribute("ordersPage", orderPage); // Đổi tên để tránh trùng lặp
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orderPage.getTotalPages());
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
            model.addAttribute("searchPhone", searchPhone);

        } else if (currentAccount != null) {
            // --- XỬ LÝ CHO CUSTOMER ---
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
    // 🔹 Hiển thị Chi tiết đơn hàng (GET /detail/{id}) - Gộp 🔹
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
            // TODO: Xử lý lấy Account từ DB nếu cần
            System.err.println("Cảnh báo: UserDetails không phải là kiểu Account.");
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
    // 🔹 Cập nhật hàng loạt trạng thái (POST /update-all-status) - Chỉ cho Staff/Admin 🔹
    // =============================================================
    @PostMapping("/update-all-status")
    // @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')") // Cách bảo mật khác bằng annotation
    public String updateAllOrderStatuses(
            @RequestParam Map<String, String> statusUpdates,
            RedirectAttributes redirectAttributes) {

        // --- Logic xử lý Map giữ nguyên như cũ ---
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

        // TODO: Nên giữ lại trang và bộ lọc hiện tại thay vì về trang 1
        return "redirect:/orders/list"; // Quay về list chung
    }

}