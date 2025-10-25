package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackSpecs;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    private AccountRepository accountRepository;

    /**
     * ✅ Tìm kiếm + lọc feedback
     * Mặc định: chỉ hiển thị feedback chưa xử lý (commentStatus != 'Allow')
     */
    @Override
    public Page<Feedback> search(String keyword, String status, Integer rating,
                                 LocalDate from, LocalDate to,
                                 int page, int size, String sortKey) {

        Specification<Feedback> spec = (root, query, cb) -> cb.conjunction();

        // 🔍 Tìm theo keyword
        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and(FeedbackSpecs.keyword(keyword));
        }

        // 🟢 Lọc theo status
        if (status != null && !"ALL".equalsIgnoreCase(status)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("commentStatus"), status));
        } else {
            // ✅ Mặc định chỉ hiện feedback chưa xử lý (chưa Allow)
            spec = spec.and((root, query, cb) ->
                    cb.notEqual(root.get("commentStatus"), "Allow"));
        }

        // ⭐ Lọc theo rating
        if (rating != null && rating > 0) {
            spec = spec.and(FeedbackSpecs.rating(rating));
        }

        // 📅 Lọc theo ngày
        if (from != null) spec = spec.and(FeedbackSpecs.dateFrom(from));
        if (to != null) spec = spec.and(FeedbackSpecs.dateTo(to));

        // 🔽 Sắp xếp
        Sort sort = toSort(sortKey);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), sort);

        return feedbackRepository.findAll(spec, pageable);
    }

    /** 🔽 Sắp xếp linh hoạt */
    private Sort toSort(String key) {
        if (key == null) key = "dateDesc";
        return switch (key) {
            case "dateAsc" -> Sort.by("createdAt").ascending();
            case "ratingDesc" -> Sort.by(Sort.Order.desc("rating"), Sort.Order.desc("createdAt"));
            case "ratingAsc" -> Sort.by(Sort.Order.asc("rating"), Sort.Order.desc("createdAt"));
            default -> Sort.by("createdAt").descending();
        };
    }

    /** 📦 Lấy feedback theo ID */
    @Override
    public Feedback get(Integer id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback không tồn tại: " + id));
    }

    /** ✉ Cập nhật hoặc thêm reply => tự động Allow */
    @Override
    @Transactional
    public void updateReply(Integer id, String reply) {
        Feedback fb = get(id);
        fb.setReply(reply == null ? null : reply.trim());
        fb.setCommentStatus("Allow"); // sau khi reply thì duyệt luôn
        feedbackRepository.save(fb);
    }

    /** 🔄 Cập nhật trạng thái riêng lẻ */
    @Override
    @Transactional
    public void updateStatus(Integer id, String status) {
        Feedback fb = get(id);
        fb.setCommentStatus(status);
        feedbackRepository.save(fb);
    }

    /** ✅ Cập nhật hàng loạt (bulk update) */
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
    @Override
    public Page<Feedback> getAllowedByProduct(Integer productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return feedbackRepository.findByProduct_ProductIdAndCommentStatusOrderByCreatedAtDesc(
                productId,
                "Allow",
                pageable
        );
    }

    @Override
    @Transactional
    public void createFeedback(Integer productId, Integer accountId, Integer rating, String comment) {
        Product product = productRepository.findById(productId).orElseThrow();
        Account account = accountRepository.findById(accountId).orElseThrow();

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
