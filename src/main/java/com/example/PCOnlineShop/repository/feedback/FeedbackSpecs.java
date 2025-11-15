package com.example.PCOnlineShop.repository.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

/**
 * Helper tạo Specification động cho Feedback (lọc theo điều kiện)
 */
public class FeedbackSpecs {

    /**  Lọc theo status */
    public static Specification<Feedback> status(String status) {
        return (root, q, cb) -> {
            if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("commentStatus"), status);
        };
    }

    /** Lọc theo rating */
    public static Specification<Feedback> rating(Integer rating) {
        return (root, q, cb) ->
                rating == null ? cb.conjunction() : cb.equal(root.get("rating"), rating);
    }
}
