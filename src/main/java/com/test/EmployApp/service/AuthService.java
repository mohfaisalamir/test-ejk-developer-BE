package com.test.EmployApp.service;

import com.test.EmployApp.dto.request.AuthRequest;
import com.test.EmployApp.dto.response.LoginResponse;
import com.test.EmployApp.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);
}
