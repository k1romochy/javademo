package com.example.demo.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.user.repository.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.item.repository.Item;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, String> Hello() {
        return Map.of("Message", "Hello World");
    }

    public List<User> Users() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        else {
            return userRepository.save(user);
        }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Transactional
    public User updateUser(Long id, String name, String email) {
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            if (name != null && !name.equals(user.getName())) {
                user.setName(name);
            }
            if (email != null && !email.equals(user.getEmail())) {
                user.setEmail(email);
            }
            return userRepository.save(user);
        }
        else {
            throw new EntityNotFoundException("User not found");
        }
    }

    public List<Item> findItemsByUserId(Long id) {
        User user = findUserById(id);
        return user.getItems();
    }

    @Transactional
    public List<Item> addUserItem(Long id, Item item) {
        User user = findUserById(id);
        user.addItem(item);
        userRepository.save(user);
        return user.getItems();
    }

    @Transactional
    public List<Item> removeUserItem(Long id, Item item) {
        User user = findUserById(id);
        user.removeItem(item);
        userRepository.save(user);
        return user.getItems();
    }

    @Transactional
    public List<Item> clearUserItems(Long id) {
        User user = findUserById(id);
        user.getItems().clear();
        userRepository.save(user);
        return user.getItems();
    }
}
