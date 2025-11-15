package com.example.PCOnlineShop.service.feedback;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.feedback.Feedback;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackRepository;
import com.example.PCOnlineShop.repository.feedback.FeedbackSpecs;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;

    /** FULL LIST – NO PAGING */
    @Override
    public List<Feedback> findAllNoPaging(String sortKey) {
        Sort sort = toSort(sortKey);
        return feedbackRepository.findAll(sort);
    }

    /** SORT RULE */
    private Sort toSort(String key) {
        if (key == null) key = "pendingFirst";

        return switch (key) {

            case "pendingFirst" ->
                    Sort.by(Sort.Order.asc("reply"), Sort.Order.desc("createdAt"));

            default -> Sort.by("createdAt").descending();
        };
    }

    /** CRUD */
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
        fb.setCommentStatus("Allow");
        feedbackRepository.save(fb);
    }

    @Override
    public Page<Feedback> getAllowedByProduct(Integer productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return feedbackRepository.findByProduct_ProductIdAndCommentStatusOrderByCreatedAtDesc(
                productId, "Allow", pageable
        );
    }

    @Override
    @Transactional
    public void createFeedback(Integer productId, Integer accountId, Integer rating, String comment) {

        boolean hasPurchased = orderDetailRepository
                .existsByOrder_Account_AccountIdAndProduct_ProductIdAndOrder_Status(
                        accountId, productId, "Completed");

        if (!hasPurchased)
            throw new IllegalArgumentException("Bạn chỉ có thể đánh giá sản phẩm đã mua!");

        List<String> banned = List.of("ngu", "điên", "vl", "cl", "dm", "chết",
                "mẹ", "đụ", "cặc", "fuck", "shit", "bitch");

        for (String word : banned)
            comment = comment.replaceAll("(?i)" + word, "***");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại!"));

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Tài khoản không tồn tại!"));

        Feedback fb = new Feedback();
        fb.setProduct(product);
        fb.setAccount(account);
        fb.setRating(rating);
        fb.setComment(comment);
        fb.setCommentStatus("Allow");
        fb.setCreatedAt(LocalDateTime.now());

        feedbackRepository.save(fb);
    }
}
