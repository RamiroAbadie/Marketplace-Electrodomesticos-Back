package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.User;

public interface UserService {
    public User register(String name, String surname, String email, String password);

    public User login(String email, String password);

    public User updateUser(Long id, String name, String surname, String email);

    public User getUserById(Long id);
}

