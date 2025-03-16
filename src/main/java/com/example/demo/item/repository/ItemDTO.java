package com.example.demo.item.repository;

public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private String price;
    private Long userId;

    public ItemDTO() {
    }

    public ItemDTO(Long id, String name, String description, String price, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.userId = userId;
    }

    public static ItemDTO fromItem(Item item) {
        if (item == null) {
            return null;
        }
        
        return new ItemDTO(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getPrice(),
            item.getUser() != null ? item.getUser().getId() : null
        );
    }

    public Item toItem() {
        Item item = new Item();
        item.setId(this.id);
        item.setName(this.name);
        item.setDescription(this.description);
        item.setPrice(this.price);
        return item;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
} 