package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUser {
    private final UserRepository userRepository;

    public GetUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(int id) {
        return userRepository.getById(id);
    }
}
