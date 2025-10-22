package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.feedback.Feedback;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Map;

public interface FeedbackService {

    Page<Feedback> search(String keyword, String status, Integer rating,
                          LocalDate from, LocalDate to,
                          int page, int size, String sortKey);

    Feedback get(Integer id);

    void updateReply(Integer id, String reply);

    void updateStatus(Integer id, String status);

    void bulkUpdateStatus(Map<Integer, String> idToStatus);
}
