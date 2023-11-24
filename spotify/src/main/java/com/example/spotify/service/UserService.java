package com.example.spotify.service;

import com.example.spotify.model.User;
import com.example.spotify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(String userName, String password) {
        User user = userRepository.findByUsername(userName);
        if(user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(User newUser) {
        if (userRepository.existsByUsername(newUser.getUserName())) {
            return null;
        }
        return userRepository.save(newUser);
    }

    public User updateUserInfo(User updatedUser) {
        if (userRepository.existsByUsername(updatedUser.getUserName())) {
            return userRepository.update(updatedUser);
        }
        return null;
    }
}

