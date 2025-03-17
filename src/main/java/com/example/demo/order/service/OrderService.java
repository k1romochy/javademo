package com.example.demo.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.item.repository.Item;
import com.example.demo.order.repository.Order;
import com.example.demo.order.repository.OrderRepository;
import com.example.demo.user.repository.User;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order with this Id not found"));
    }

    public void removeOrderById(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            orderRepository.deleteById(id);
        } else {
            throw new RuntimeException("Order with this Id not found");
        }
    }

    @Transactional
    public List<Item> addItem(Long orderId, Item item) {
        if (orderRepository.findById(orderId).isPresent()) {
            Order order = getOrderById(orderId);
            order.addItem(item);

            orderRepository.save(order);
            return order.getItems();
        } else {
            throw new RuntimeException("Order with this Id not found");
        }
    }

    @Transactional
    public List<Item> removeItem(Long orderId, Item item) {
        if (orderRepository.findById(orderId).isPresent()) {
            Order order = getOrderById(orderId);
            order.removeItem(item);

            orderRepository.save(order);
            return order.getItems();
        } else {
            throw new RuntimeException("Order with this Id not found");
        }
    }

    public User getUserWithOrderId(Long id) {
        if (orderRepository.findById(id).isPresent()) {
            Order order = getOrderById(id);

            return order.getUser();
        } else {
            throw new RuntimeException("Order with this Id not found");        
        }
    }

    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
