package com.uade.tpo.marketplace.controllers;

import com.uade.tpo.marketplace.entity.User;
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
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getRole()
        );

        return ResponseEntity.ok(response);
    }

}

