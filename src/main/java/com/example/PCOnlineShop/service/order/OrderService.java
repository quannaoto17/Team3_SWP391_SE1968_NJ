package com.example.PCOnlineShop.service.order;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.order.Order;
import com.example.PCOnlineShop.model.order.OrderDetail;
import com.example.PCOnlineShop.repository.order.OrderDetailRepository;
import com.example.PCOnlineShop.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Order> getOrdersByAccount(Account account) {
        return orderRepository.findByAccount(account);
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        Order order = getOrderById(orderId);
        if (order != null)
            return orderDetailRepository.findByOrder(order);
        return Collections.emptyList();
    }

    // ...
    public void updateOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(newStatus);
            orderRepository.save(order);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
