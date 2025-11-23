package com.example.PCOnlineShop.repository.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
    Page<Feedback> findByProduct_ProductIdAndCommentStatusOrderByCreatedAtDesc(
            int productId,
            String commentStatus,
            Pageable pageable);
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.product.productId = :productId AND f.commentStatus = 'Allow'")
    Double getAverageRating(@Param("productId") Integer productId);

}
