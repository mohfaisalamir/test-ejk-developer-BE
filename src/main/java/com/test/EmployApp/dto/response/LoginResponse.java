package com.test.EmployApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;   // JWT Token
    private String role;     // Optional: role of the authenticated user

    // Additional fields can be added if needed
}
