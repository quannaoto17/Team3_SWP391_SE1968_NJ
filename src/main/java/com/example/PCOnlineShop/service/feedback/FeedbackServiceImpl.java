package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.repository.feedback.FeedbackRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackSpecs;
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

    @Override
    public Page<Feedback> search(String keyword, String status, Integer rating,
                                 LocalDate from, LocalDate to,
                                 int page, int size, String sortKey) {

        Specification<Feedback> spec = (root, query, cb) -> cb.conjunction();
        spec = spec.and(FeedbackSpecs.keyword(keyword));

        // chỉ hiển thị feedback chưa xử lý (chưa reply hoặc chưa Allow)
        spec = spec.and((root, query, cb) ->
                cb.or(cb.isNull(root.get("reply")),
                        cb.notEqual(root.get("commentStatus"), "Allow"))
        );

        if (rating != null && rating > 0)
            spec = spec.and(FeedbackSpecs.rating(rating));
        if (from != null)
            spec = spec.and(FeedbackSpecs.dateFrom(from));
        if (to != null)
            spec = spec.and(FeedbackSpecs.dateTo(to));

        Sort sort = toSort(sortKey);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), sort);
        return feedbackRepository.findAll(spec, pageable);
    }

    private Sort toSort(String key) {
        if (key == null) key = "dateDesc";
        return switch (key) {
            case "dateAsc" -> Sort.by("createdAt").ascending();
            case "ratingDesc" -> Sort.by(Sort.Order.desc("rating"), Sort.Order.desc("createdAt"));
            case "ratingAsc" -> Sort.by(Sort.Order.asc("rating"), Sort.Order.desc("createdAt"));
            default -> Sort.by("createdAt").descending();
        };
    }

    @Override
    public Feedback get(Integer id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback không tồn tại: " + id));
    }

    @Override
    @Transactional
    public void updateReply(Integer id, String reply) {
        Feedback fb = get(id);
        fb.setReply(reply == null ? null : reply.trim());
        fb.setCommentStatus("Allow"); // sau khi reply → tự động duyệt
        feedbackRepository.save(fb);
    }

    @Transactional
    public void updateStatus(Integer id, String status) {
        Feedback fb = get(id);
        fb.setCommentStatus(status);
        feedbackRepository.save(fb);
    }

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
}
