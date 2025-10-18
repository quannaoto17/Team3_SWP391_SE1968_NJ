package com.example.PCOnlineShop.controller.order; // Đảm bảo đúng package

import com.example.PCOnlineShop.dto.order.OrderSearchRequest;
import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/staff/orders")
public class StaffOrderController {

    private final OrderService orderService;
    private final int PAGE_SIZE = 10;
    // Inject AccountRepository nếu cần lấy danh sách Shipper
    // private final AccountRepository accountRepository;

    public StaffOrderController(OrderService orderService /*, AccountRepository accountRepository*/) {
        this.orderService = orderService;
        // this.accountRepository = accountRepository;
    }

    // ======================================================
    // 🔹 Hiển thị Danh sách đơn hàng (GET /list) 🔹
    // ======================================================
    @GetMapping("/list")
    public String viewAllOrders(Model model,
                                @Valid OrderSearchRequest searchRequest, // Sử dụng DTO
                                BindingResult bindingResult,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "sort", defaultValue = "createdDate") String sortField,
                                @RequestParam(name = "dir", defaultValue = "asc") String sortDir,
                                RedirectAttributes redirectAttributes) {

        String searchPhone = searchRequest.getSearchPhone();

        // Xử lý lỗi validation từ DTO
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/staff/orders/list";
        }

        // Kiểm tra khách hàng tồn tại
        if (StringUtils.hasText(searchPhone)) {
            if (!orderService.customerAccountExistsByPhoneNumber(searchPhone)) {
                redirectAttributes.addFlashAttribute("error", "No customer found with phone number: " + searchPhone);
                return "redirect:/staff/orders/list";
            }
        }

        // Sắp xếp
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        // Phân trang
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);
        Page<Order> orderPage = orderService.findPaginated(pageable, searchPhone);

        // Gửi dữ liệu sang view
        model.addAttribute("orders", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("searchPhone", searchPhone);

        // Lấy danh sách shipper nếu cần cho dropdown (bạn cần inject AccountRepository)
        // model.addAttribute("shippers", accountRepository.findByRole(RoleName.Shipper));

        return "stafforder/staff-order-list";
    }

    // ==============================
    // 🔹 Hiển thị Chi tiết đơn hàng (GET /detail/{id}) 🔹
    // ==============================
    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/staff/orders/list";
        }
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));

        // Lấy danh sách shipper nếu cần gán đơn từ trang detail
        // model.addAttribute("shippers", accountRepository.findByRole(RoleName.Shipper));

        return "stafforder/staff-order-detail";
    }

    // ==============================
    // 🔹 Cập nhật hàng loạt trạng thái (POST /update-all-status) 🔹
    // ==============================
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

        // Tạm thời quay về trang 1
        return "redirect:/staff/orders/list";
    }

    /**
     * GET /staff/orders/my-deliveries
     */
    @GetMapping("/my-deliveries")
    public String viewMyDeliveries(@AuthenticationPrincipal Account currentStaff, Model model, RedirectAttributes redirectAttributes) {
        if (currentStaff == null) {
            redirectAttributes.addFlashAttribute("error", "User not logged in.");
            return "redirect:/auth/login";
        }
        // Kiểm tra có phải Staff không (nếu Security chưa chặn)
        /* if(currentStaff.getRole() != RoleName.Staff) {
             redirectAttributes.addFlashAttribute("error", "Access Denied.");
             return "redirect:/home"; // Hoặc trang lỗi
        } */
        model.addAttribute("assignedOrders", orderService.getAssignedOrdersForStaffMember(currentStaff));
        model.addAttribute("staffName", currentStaff.getFullName());
        return "stafforder/my-delivery-list"; // Trả về file HTML mới
    }

    /**
     * POST /staff/orders/update-delivery-status/{orderId}
     */
    @PostMapping("/update-delivery-status/{orderId}")
    public String updateDeliveryStatus(@PathVariable int orderId,
                                       @RequestParam String newStatus,
                                       @AuthenticationPrincipal Account currentStaff,
                                       RedirectAttributes redirectAttributes) {
        if (currentStaff == null) {
            redirectAttributes.addFlashAttribute("error", "User not logged in.");
            return "redirect:/auth/login";
        }
        try {
            orderService.updateOrderStatusByStaffShipper(orderId, newStatus, currentStaff);
            redirectAttributes.addFlashAttribute("success", "Order #" + orderId + " status updated to " + newStatus);
        } catch (Exception e) {
            System.err.println("Lỗi staff cập nhật delivery status: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error updating status: " + e.getMessage());
        }
        return "redirect:/staff/orders/my-deliveries"; // Quay lại danh sách giao hàng
    }
}