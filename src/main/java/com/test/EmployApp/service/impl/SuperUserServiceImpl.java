package com.test.EmployApp.service.impl;

import com.test.EmployApp.dto.request.UpdateUserRequest;
import com.test.EmployApp.dto.response.UserResponse;
import com.test.EmployApp.entity.User;
import com.test.EmployApp.repository.UserRepository;
import com.test.EmployApp.service.SuperUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SuperUserServiceImpl implements SuperUserService {
    private final UserRepository userRepository;
    @Override
    public void createNew(User user) {
        try {
            userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }
    }

    @Override
    public UserResponse update(UpdateUserRequest request) {
        try {
            User user = findByIdOrThrowNotFound(request.getId()); //findByIdOrThrowNotFound(request.getId());
            user.setName(request.getName());
            userRepository.saveAndFlush(user);
            return UserResponse.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .userCredentialId(user.getUserCredential().getId())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user already exist");
        }

    }

    private User findByIdOrThrowNotFound(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }
}
