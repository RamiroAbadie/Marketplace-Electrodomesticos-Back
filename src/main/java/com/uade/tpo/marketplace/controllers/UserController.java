package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.User;
import com.uade.tpo.marketplace.entity.dto.LoginRequest;
import com.uade.tpo.marketplace.entity.dto.auth.RegisterRequest;
import com.uade.tpo.marketplace.entity.dto.UpdateUserRequest;
import com.uade.tpo.marketplace.entity.dto.UserResponse;
import com.uade.tpo.marketplace.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) {
        User user = userService.register(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UpdateUserRequest request) {
        User user = userService.updateUser(
            id,
            request.getName(),
            request.getSurname(),
            request.getEmail()
        );
        return ResponseEntity.ok(user);
    }

    // La idea es que sirva para el ADMIN y para que el usuario vea solo SUS propios datos
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        // Notar que no devuelve la contrasena ;)
        UserResponse response = new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole()
        );

        return ResponseEntity.ok(response);
    }

}

