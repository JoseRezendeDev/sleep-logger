package com.noom.interview.fullstack.sleep.service.adapter;

import com.noom.interview.fullstack.sleep.model.User;

public interface UserRepository {
    User findById(int id);
}
