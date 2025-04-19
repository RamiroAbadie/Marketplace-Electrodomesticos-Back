package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.User;

public interface UserService {
    public User updateUser(Long id, String name, String surname, String email);

    public User getUserById(Long id);
}

