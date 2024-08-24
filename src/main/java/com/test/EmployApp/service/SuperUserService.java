package com.test.EmployApp.service;

import com.test.EmployApp.dto.request.UpdateUserRequest;
import com.test.EmployApp.dto.response.UserResponse;
import com.test.EmployApp.entity.User;

public interface SuperUserService {
    void createNew(User user);
    UserResponse update(UpdateUserRequest request);
}
