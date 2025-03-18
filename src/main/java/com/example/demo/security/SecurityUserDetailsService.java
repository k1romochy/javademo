package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.user.repository.User;
import com.example.demo.user.repository.UserRepositoryDatabase;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    
    private final UserRepositoryDatabase userRepository;
    
    public SecurityUserDetailsService(UserRepositoryDatabase userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return new SecurityUser(user);
    }
} 