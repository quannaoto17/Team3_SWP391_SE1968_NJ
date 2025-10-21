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

        spec = spec.and(FeedbackSpecs.keyword(keyword))
                .and(FeedbackSpecs.status(status))
                .and(FeedbackSpecs.rating(rating))
                .and(FeedbackSpecs.dateFrom(from))
                .and(FeedbackSpecs.dateTo(to));


        Sort sort = toSort(sortKey);
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), sort);
        return feedbackRepository.findAll(spec, pageable);
    }

    private Sort toSort(String key) {
        // mặc định: newest first
        if (key == null) key = "dateDesc";
        return switch (key) {
            case "dateAsc" -> Sort.by("createdAt").ascending();
            case "ratingDesc" -> Sort.by(Sort.Order.desc("rating"), Sort.Order.desc("createdAt"));
            case "ratingAsc" -> Sort.by(Sort.Order.asc("rating"), Sort.Order.desc("createdAt"));
            case "statusAsc" -> Sort.by(Sort.Order.asc("commentStatus"), Sort.Order.desc("createdAt"));
            default -> Sort.by("createdAt").descending(); // dateDesc
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
        feedbackRepository.save(fb);
    }

    @Override
    @Transactional
    public void bulkUpdateStatus(Map<Integer, String> idToStatus) {
        if (idToStatus == null || idToStatus.isEmpty()) return;
        idToStatus.forEach((id, st) -> {
            Feedback fb = get(id);
            fb.setCommentStatus(st);
            feedbackRepository.save(fb);
        });
    }
}
