package com.example.PCOnlineShop.controller.order;

import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.service.order.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/staff/orders")
public class StaffOrderController {

    private final OrderService orderService;
    private final int PAGE_SIZE = 10;

    public StaffOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ======================================================
    // 🔹 Danh sách đơn hàng (có tìm kiếm, phân trang, sắp xếp)
    // ======================================================
    @GetMapping("/list")
    public String viewAllOrders(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "sort", defaultValue = "createdDate") String sortField,
                                @RequestParam(name = "dir", defaultValue = "asc") String sortDir,
                                @RequestParam(name = "searchPhone", required = false) String searchPhone,
                                RedirectAttributes redirectAttributes) {

        if (StringUtils.hasText(searchPhone)) {
            if (!searchPhone.matches("\\d+")) {
                redirectAttributes.addFlashAttribute("error", "Số điện thoại chỉ được chứa các ký tự số!");
                return "redirect:/staff/orders/list";
            }
            if (!orderService.customerAccountExistsByPhoneNumber(searchPhone)) {
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy khách hàng nào với số điện thoại: " + searchPhone);
                return "redirect:/staff/orders/list";
            }
        }

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE, sort);
        Page<Order> orderPage = orderService.findPaginated(pageable, searchPhone);

        model.addAttribute("orders", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("searchPhone", searchPhone);

        return "stafforder/staff-order-list";
    }

    // ==============================
    // 🔹 Xem chi tiết đơn hàng
    // ==============================
    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/staff/orders/list";
        }
        model.addAttribute("order", order);
        model.addAttribute("details", orderService.getOrderDetails(id));
        return "stafforder/staff-order-detail"; // Cần có view này
    }

    // ==============================
    // 🔹 Cập nhật trạng thái đơn hàng
    // ==============================
    @PostMapping("/update-status/{id}")
    public String updateOrderStatus(@PathVariable int id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/staff/orders/list";
    }
}
