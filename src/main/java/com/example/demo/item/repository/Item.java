package com.example.demo.item.repository;

import java.util.List;
import java.util.ArrayList;

import com.example.demo.order.repository.Order;
import com.example.demo.user.repository.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private String price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "items")
    private List<Order> orders = new ArrayList<>();
    
    public Item() {
    }

    public Item(Long id, String name, String description, String price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }   

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clearUser() {
        this.user = null;
    }
    
    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }
}
