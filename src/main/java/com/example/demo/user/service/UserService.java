package com.example.demo.user.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.user.repository.User;
import com.example.demo.user.repository.UserRepositoryDatabase;
import com.example.demo.user.repository.UserRepositoryRedis;
import com.example.demo.item.repository.Item;
import org.springframework.data.redis.core.RedisTemplate;

@Service
public class UserService {

    private final UserRepositoryDatabase userRepositoryDatabase;
    private final UserRepositoryRedis userRepositoryRedis;
    private final RedisTemplate<String, User> userRedisTemplate;

    public UserService(UserRepositoryDatabase userRepositoryDatabase, 
                      UserRepositoryRedis userRepositoryRedis,
                      RedisTemplate<String, User> userRedisTemplate) {
        this.userRepositoryDatabase = userRepositoryDatabase;
        this.userRepositoryRedis = userRepositoryRedis;
        this.userRedisTemplate = userRedisTemplate;
    }

    public Map<String, String> Hello() {
        return Map.of("Message", "Hello World");
    }

    public List<User> Users() {
        return userRepositoryDatabase.findAll();
    }

    @Transactional
    public User createUser(User user) {
        Optional<User> existingUser = userRepositoryDatabase.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        else {
            User savedUser = userRepositoryDatabase.save(user);
            try {
                System.out.println("Attempting to save user to Redis via Repository with ID: " + savedUser.getId());
                System.out.println("User object being saved: " + savedUser.getName() + ", " + savedUser.getEmail());
                
                userRepositoryRedis.save(savedUser);
                
                Optional<User> redisUser = userRepositoryRedis.findById(savedUser.getId());
                if (redisUser.isPresent()) {
                    System.out.println("Successfully verified user in Redis: " + redisUser.get().getName());
                } else {
                    System.out.println("WARNING: Could not find user in Redis after save!");
                }
                
                System.out.println("Completed Redis operations");
            } catch (Exception e) {
                System.err.println("Failed to save user to Redis: " + e.getMessage());
                e.printStackTrace();
            }
            
            return savedUser;
        }
    }

    public User findUserById(Long id) {
        return userRepositoryDatabase.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void deleteUserById(Long id) {
        if (userRepositoryDatabase.findById(id).isPresent()) {
            userRepositoryDatabase.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Transactional
    public User updateUser(Long id, String name, String email) {
        if (userRepositoryDatabase.findById(id).isPresent()) {
            User user = userRepositoryDatabase.findById(id).get();
            if (name != null && !name.equals(user.getName())) {
                user.setName(name);
            }
            if (email != null && !email.equals(user.getEmail())) {
                user.setEmail(email);
            }
            return userRepositoryDatabase.save(user);
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
        userRepositoryDatabase.save(user);
        return user.getItems();
    }

    @Transactional
    public List<Item> removeUserItem(Long id, Item item) {
        User user = findUserById(id);
        user.removeItem(item);
        userRepositoryDatabase.save(user);
        return user.getItems();
    }

    @Transactional
    public List<Item> clearUserItems(Long id) {
        User user = findUserById(id);
        user.getItems().clear();
        userRepositoryDatabase.save(user);
        return user.getItems();
    }
}
