package com.example.demo.item.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.item.service.ItemService;
import com.example.demo.security.SecurityUser;
import com.example.demo.user.repository.UserDTO;
import com.example.demo.item.repository.Item;
import com.example.demo.item.repository.ItemDTO;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping("/")
    public List<ItemDTO> findAllItems() {
        return itemService.findAllItems()
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}/")
    public ItemDTO findItemById(@PathVariable Long id) {
        return ItemDTO.fromItem(itemService.findItemById(id));
    }

    @PostMapping("/")
    public ItemDTO createItem(@RequestBody ItemDTO itemDTO, 
                                @AuthenticationPrincipal SecurityUser securityUser) {
        itemDTO.setId(null);
        Item item = itemDTO.toItem();
        return ItemDTO.fromItem(itemService.createItem(item, securityUser));
    }

    @PutMapping("/{id}/")
    public ItemDTO updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        Item item = itemDTO.toItem();
        return ItemDTO.fromItem(itemService.updateItem(id, item));
    }   
    
    @DeleteMapping("/{id}/")
    public void deleteItemById(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }

    @GetMapping("/{id}/user/")
    public UserDTO getUserByItemId(@PathVariable Long id) {
        return UserDTO.fromUser(itemService.getUserByItemId(id));
    }
}
