package com.se.ecommerce.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserCreateRequest {
    @Email
    private String email;
    @NotBlank( message = "not blank")
    private String password;
    @NotBlank
    @NotEmpty
    private String userName;
}
