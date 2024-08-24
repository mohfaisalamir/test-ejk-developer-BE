package com.test.EmployApp.service.impl;

import com.test.EmployApp.dto.request.AuthRequest;
import com.test.EmployApp.dto.response.LoginResponse;
import com.test.EmployApp.dto.response.RegisterResponse;
import com.test.EmployApp.entity.AppUser;
import com.test.EmployApp.repository.UserCredentialRepository;
import com.test.EmployApp.security.JwtUtil;
import com.test.EmployApp.service.AuthService;
import com.test.EmployApp.service.EmployeeService;
import com.test.EmployApp.service.RoleService;
import com.test.EmployApp.service.UserService;
import com.test.EmployApp.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final RoleService roleService;
    private final UserService userService;
    private final JwtUtil jwtUtil;// teko kene
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        return null;
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        // Autentikasi pengguna menggunakan AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Set autentikasi ke SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Hasilkan token JWT menggunakan JwtUtil
        String jwt = jwtUtil.generateToken(authentication);

        // Kembalikan token sebagai bagian dari LoginResponse
        return LoginResponse.builder()
                .token(jwt)
                .userName(request.getUsername())
                .build();
    }

}
