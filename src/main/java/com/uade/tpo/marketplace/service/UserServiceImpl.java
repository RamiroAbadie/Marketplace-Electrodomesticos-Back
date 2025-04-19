package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.repository.UserRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User updateUser(Long id, String name, String surname, String email) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();
        user.setFirstname(name);
        user.setLastname(surname);
        user.setEmail(email);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}

