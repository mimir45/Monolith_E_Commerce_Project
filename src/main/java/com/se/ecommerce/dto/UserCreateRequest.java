package com.se.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String email;
    private String password;
    private String userName;
}
