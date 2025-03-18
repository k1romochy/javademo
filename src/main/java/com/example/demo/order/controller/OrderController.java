package com.example.demo.order.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.item.repository.Item;
import com.example.demo.item.repository.ItemDTO;
import com.example.demo.item.service.ItemService;
import com.example.demo.order.repository.Order;
import com.example.demo.order.repository.OrderDTO;
import com.example.demo.order.service.OrderService;
import com.example.demo.user.repository.User;
import com.example.demo.user.repository.UserDTO;
import com.example.demo.user.service.UserService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    public OrderController(OrderService orderService, UserService userService, ItemService itemService) {
        this.orderService = orderService;
        this.userService = userService;
        this.itemService = itemService;
    }
    
    @GetMapping("/")
    public List<OrderDTO> getAllOrders() {
        return orderService.findAllOrders()
            .stream()
            .map(OrderDTO::fromOrder)
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}/")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return OrderDTO.fromOrder(orderService.getOrderById(id));
    }
    
    @PostMapping("/")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        
        if (orderDTO.getUserId() != null) {
            User user = userService.findUserById(orderDTO.getUserId());
            order.setUser(user);
        }
        
        Order savedOrder = orderService.saveOrder(order);
        
        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            for (Item requestItem : orderDTO.getItems()) {
                if (requestItem.getId() != null) {
                    Item item = itemService.findItemById(requestItem.getId());
                    orderService.addItem(savedOrder.getId(), item);
                }
            }
            savedOrder = orderService.getOrderById(savedOrder.getId());

        }
        
        return OrderDTO.fromOrder(savedOrder);
    }
    
    @DeleteMapping("/{id}/")
    public void deleteOrder(@PathVariable Long id) {
        orderService.removeOrderById(id);
    }
    
    @PostMapping("/{orderId}/items/{itemId}/")
    public List<ItemDTO> addItemToOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        
        return orderService.addItem(orderId, item)
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }
    
    @DeleteMapping("/{orderId}/items/{itemId}/")
    public List<ItemDTO> removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        Item item = itemService.findItemById(itemId);
        
        return orderService.removeItem(orderId, item)
            .stream()
            .map(ItemDTO::fromItem)
            .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}/user/")
    public UserDTO getUserByOrderId(@PathVariable Long id) {
        return UserDTO.fromUser(orderService.getUserWithOrderId(id));
    }
}
