package com.example.demo.order.repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.item.repository.Item;

public class OrderDTO {
    private Long id;
    private LocalDateTime created_at;
    private Long UserId;
    private List<Item> items;

    public OrderDTO(){}

    public OrderDTO(Long id, Long UserId, LocalDateTime created_at, List<Item> items) {
        this.id = id;
        this.UserId = UserId;
        this.created_at = created_at;
        this.items = items != null ? items : new ArrayList<>();
    }

    public OrderDTO(Long id, Long UserId, LocalDateTime created_at) {
        this.id = id;
        this.UserId = UserId;
        this.created_at = created_at;
        this.items = new ArrayList<>();
    }

    public static OrderDTO fromOrder(Order order) {
        if (order == null) {
            return null;
        }
        
        return new OrderDTO(
            order.getId(),
            order.getUser() != null ? order.getUser().getId() : null,
            order.getCreatedAt(),
            order.getItems()
        );
    }

    public Order toOrder() {
        Order order = new Order();
        order.setId(this.id);
        order.setCreatedAt(this.created_at);
        
        if (this.items != null) {
            for (Item item : this.items) {
                order.addItem(item);
            }
        }
        
        return order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        this.UserId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
