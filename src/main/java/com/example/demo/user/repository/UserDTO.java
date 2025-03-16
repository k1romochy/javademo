package com.example.demo.user.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.item.repository.ItemDTO;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<ItemDTO> items;

    public UserDTO() {
        this.items = new ArrayList<>();
    }

    public UserDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.items = new ArrayList<>();
    }

    public UserDTO(Long id, String name, String email, List<ItemDTO> items) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.items = items != null ? items : new ArrayList<>();
    }

    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    public static UserDTO fromUserWithItems(User user) {
        if (user == null) {
            return null;
        }
        
        List<ItemDTO> itemDTOs = null;
        if (user.getItems() != null) {
            itemDTOs = user.getItems().stream()
                .map(ItemDTO::fromItem)
                .collect(Collectors.toList());
        }
        
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            itemDTOs
        );
    }

    public User toUser() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setEmail(this.email);
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
} 