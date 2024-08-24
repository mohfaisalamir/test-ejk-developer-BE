package com.test.EmployApp.service;

import com.test.EmployApp.entity.Role;

public interface RoleService {
    Role getOrSave(Role role);
}