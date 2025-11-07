package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackSpecs;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;

    /**  Tìm kiếm và lọc feedback cho staff */
    @Override
    public Page<Feedback> search(String status, Integer rating,
                                 LocalDate from, LocalDate to,
                                 int page, int size, String sortKey) {

        Specification<Feedback> spec = (root, query, cb) -> cb.conjunction();

        // Lọc theo status
        if (status != null && !"ALL".equalsIgnoreCase(status)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("commentStatus"), status));
        } else {
            // Mặc định chỉ hiển thị feedback chưa duyệt
            spec = spec.and((root, query, cb) ->
                    cb.notEqual(root.get("commentStatus"), "Allow"));
        }

        // Lọc theo rating
        if (rating != null && rating > 0) {
            spec = spec.and(FeedbackSpecs.rating(rating));
        }

        // Lọc theo ngày
        if (from != null) spec = spec.and(FeedbackSpecs.dateFrom(from));
        if (to != null) spec = spec.and(FeedbackSpecs.dateTo(to));

        // Sắp xếp
        Sort sort = toSort(sortKey);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), sort);

        return feedbackRepository.findAll(spec, pageable);
    }

    /**  Hàm hỗ trợ sắp xếp linh hoạt */
    private Sort toSort(String key) {
        if (key == null) key = "dateDesc";
        return switch (key) {
            case "dateAsc" -> Sort.by("createdAt").ascending();
            case "ratingDesc" -> Sort.by(Sort.Order.desc("rating"), Sort.Order.desc("createdAt"));
            case "ratingAsc" -> Sort.by(Sort.Order.asc("rating"), Sort.Order.desc("createdAt"));
            default -> Sort.by("createdAt").descending();
        };
    }

    /**  Lấy feedback theo ID */
    @Override
    public Feedback get(Integer id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback không tồn tại: " + id));
    }

    /**  Cập nhật hoặc thêm reply => tự động Allow */
    @Override
    @Transactional
    public void updateReply(Integer id, String reply) {
        Feedback fb = get(id);
        fb.setReply(reply == null ? null : reply.trim());
        fb.setCommentStatus("Allow");
        feedbackRepository.save(fb);
    }

    /**  Cập nhật trạng thái riêng lẻ */
    @Override
    @Transactional
    public void updateStatus(Integer id, String status) {
        Feedback fb = get(id);
        fb.setCommentStatus(status);
        feedbackRepository.save(fb);
    }

    /**  Cập nhật hàng loạt (bulk update) */
    @Override
    @Transactional
    public void bulkUpdateStatus(Map<Integer, String> idToStatus) {
        if (idToStatus == null || idToStatus.isEmpty()) return;

        idToStatus.forEach((id, st) -> {
            if ("Allow".equalsIgnoreCase(st)) {
                Feedback fb = get(id);
                fb.setCommentStatus("Allow");
                feedbackRepository.save(fb);
            }
        });
    }

    /**  Lấy feedback Allow theo sản phẩm */
    @Override
    public Page<Feedback> getAllowedByProduct(Integer productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return feedbackRepository.findByProduct_ProductIdAndCommentStatusOrderByCreatedAtDesc(
                productId, "Allow", pageable
        );
    }

    /**  Tạo mới feedback — chỉ cho phép nếu user đã mua hàng */
    @Override
    @Transactional
    public void createFeedback(Integer productId, Integer accountId, Integer rating, String comment) {

        // Kiểm tra user đã mua sản phẩm chưa (đơn hàng Completed)
        boolean hasPurchased = orderDetailRepository
                .existsByOrder_Account_AccountIdAndProduct_ProductIdAndOrder_Status(
                        accountId, productId, "Completed"
                );

        if (!hasPurchased) {
            throw new IllegalArgumentException("Bạn chỉ có thể đánh giá sản phẩm đã mua!");
        }

        //  Lấy product & account
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại!"));
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại!"));

        // Tạo feedback
        Feedback feedback = new Feedback();
        feedback.setProduct(product);
        feedback.setAccount(account);
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCommentStatus("Pending");
        feedback.setCreatedAt(LocalDate.now());

        feedbackRepository.save(feedback);
    }
}
