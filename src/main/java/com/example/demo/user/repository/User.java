package com.example.demo.user.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.item.repository.Item;
import com.example.demo.order.repository.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User() {
    }

    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }   

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        item.setUser(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.clearUser();
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
