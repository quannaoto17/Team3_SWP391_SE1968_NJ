package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.dto.order.OrderSearchRequest;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
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

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final int PAGE_SIZE = 10;

    // THÊM TRƯỜNG NÀY
    private final AccountRepository accountRepository;

    // SỬA CONSTRUCTOR
    public OrderController(OrderService orderService, AccountRepository accountRepository) {
        this.orderService = orderService;
        this.accountRepository = accountRepository; // THÊM DÒNG NÀY
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

}