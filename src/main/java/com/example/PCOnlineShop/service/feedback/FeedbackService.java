package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.Map;

public interface FeedbackService {

    Page<Feedback> search(String status, Integer rating,
                          LocalDate from, LocalDate to,
                          int page, int size, String sortKey);

    Feedback get(Integer id);

    void updateReply(Integer id, String reply);

    void updateStatus(Integer id, String status);


    Page<Feedback> getAllowedByProduct(Integer productId, int page, int size);

    void createFeedback(Integer productId, Integer accountId, Integer rating, String comment);
}
