package com.test.EmployApp.repository;

import com.test.EmployApp.constant.ERole;
import com.test.EmployApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole roleName);
}
