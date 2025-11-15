package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedbackService {

    Page<Feedback> getAllowedByProduct(Integer productId, int page, int size);

    List<Feedback> findAllNoPaging(String sortKey);

    Feedback get(Integer id);

    void updateReply(Integer id, String reply);

    void createFeedback(Integer productId, Integer accountId, Integer rating, String comment);
}

