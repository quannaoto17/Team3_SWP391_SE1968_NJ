package com.example.PCOnlineShop.repository.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FeedbackSpecs {

    public static Specification<Feedback> keyword(String kw) {
        return (root, q, cb) -> {
            if (kw == null || kw.isBlank()) return cb.conjunction();
            var acc = root.join("account", JoinType.LEFT);
            var prod = root.join("product", JoinType.LEFT);
            String like = "%" + kw.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("comment")), like),
                    cb.like(cb.lower(root.get("reply")), like),
                    cb.like(cb.lower(acc.get("email")), like),
                    cb.like(cb.lower(acc.get("phoneNumber")), like),
                    cb.like(cb.lower(acc.get("firstName")), like),
                    cb.like(cb.lower(acc.get("lastName")), like),
                    cb.like(cb.lower(prod.get("productName")), like)
            );
        };
    }

    public static Specification<Feedback> status(String status) {
        return (root, q, cb) -> {
            if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
                return cb.conjunction();
            }
            return cb.equal(root.get("commentStatus"), status);
        };
    }

    public static Specification<Feedback> rating(Integer rating) {
        return (root, q, cb) -> rating == null ? cb.conjunction() : cb.equal(root.get("rating"), rating);
    }

    public static Specification<Feedback> dateFrom(LocalDate from) {
        return (root, q, cb) -> from == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("createdAt"), from);
    }

    public static Specification<Feedback> dateTo(LocalDate to) {
        return (root, q, cb) -> to == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("createdAt"), to);
    }
}
