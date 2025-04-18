package com.uade.tpo.marketplace.entity.dto;

import com.uade.tpo.marketplace.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Role role;
}
