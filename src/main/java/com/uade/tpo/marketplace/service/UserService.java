package com.uade.tpo.marketplace.service;

import java.util.List;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.dto.UserResponse;

public interface UserService {
    public User updateUser(Long id, String name, String surname, String email, String password);

    public User getUserById(Long id);

    public List<UserResponse> findAll();
}

