package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Role;
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
    public User register(String name, String surname, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password); // por ahora se esta guardando en texto plano
        user.setRole(Role.USER); // Aca definimos el rol, de forma interna aca en el serivce
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    @Override
    public User updateUser(Long id, String name, String surname, String email) {
        // Estamos usando un método que ya viene implementado por Spring Data JPA
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();
        user.setName(name);
        user.setSurname(surname);
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

