package com.example.demo.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.item.repository.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.security.SecurityUser;
import com.example.demo.user.repository.User;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemById(Long id) {
        return itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Transactional
    public Item createItem(Item item, SecurityUser securityUser) {
        if (itemRepository.findByName(item.getName()).isPresent()) {
            throw new RuntimeException("Item already exists");
        }
        else {
            User user = securityUser.getUser();
            item.setUser(user);
            return itemRepository.save(item);
        }
    }

    @Transactional
    public Item updateItem(Long id, Item item) {
        if (itemRepository.findById(id).isPresent()) {
            Item existingItem = findItemById(id);
            if (item.getName() != null && existingItem.getName() != item.getName()) {
                existingItem.setName(item.getName());
            }
            if (item.getPrice() != null && existingItem.getPrice() != item.getPrice()) {
                existingItem.setPrice(item.getPrice());
            }
            return itemRepository.save(existingItem);
        }
        else {
            throw new RuntimeException("Item not found");
        }
    }

    public void deleteItemById(Long id) {
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("Item not found");
        }
    }

    public User getUserByItemId(Long id) {
        Item item = findItemById(id);
        return item.getUser();
    }
}
