package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.exception.UserNotFoundException;
import com.noom.interview.fullstack.sleep.model.User;
import com.noom.interview.fullstack.sleep.service.adapter.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GetUser {
    private final UserRepository userRepository;

    public GetUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(int id) {
        try {
            return userRepository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("Cannot find user with ID " + id);
        }
    }
}
