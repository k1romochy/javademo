package com.example.demo.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.item.repository.Item;
import com.example.demo.item.repository.ItemDTO;
import com.example.demo.item.service.ItemService;
import com.example.demo.user.repository.User;
import com.example.demo.user.repository.UserDTO;
import com.example.demo.user.service.UserService;

@RestController
@RequestMapping("/users/")
public class UserController {
    private final UserService userService;
    private final ItemService itemService;
    
    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }
    
    @GetMapping("/")
    public List<UserDTO> Users() {
        return userService.Users()
            .stream()
            .map(UserDTO::fromUser)
            .collect(Collectors.toList());
    }

    @PostMapping("/")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        userDTO.setId(null);
        User user = userDTO.toUser();
        return UserDTO.fromUser(userService.createUser(user));
    }

    @DeleteMapping("{id}/")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("{id}/")
    public UserDTO findUserById(@PathVariable Long id) {
        return UserDTO.fromUser(userService.findUserById(id));
    }

    @PutMapping("{id}/")
    public UserDTO updateUser(@PathVariable Long id,
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String email) {
        return UserDTO.fromUser(userService.updateUser(id, name, email));
    }

    @GetMapping("{id}/items/")
    public List<ItemDTO> findItemsByUserId(@PathVariable Long id) {
        return userService.findItemsByUserId(id)
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }

    @PostMapping("{id}/{itemId}/")
    public List<ItemDTO> addUserItem(@PathVariable Long id, @PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        return userService.addUserItem(id, item)
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }
    
    @DeleteMapping("{id}/{itemId}/")
    public List<ItemDTO> removeUserItem(@PathVariable Long id, @PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        return userService.removeUserItem(id, item)
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }
}
