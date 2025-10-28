package com.example.PCOnlineShop.repository.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
    Page<Feedback> findByProduct_ProductIdAndCommentStatusOrderByCreatedAtDesc(
            int productId,
            String commentStatus,
            Pageable pageable);

}
