package com.test.EmployApp.service;

import com.test.EmployApp.dto.request.NewEmployeeRequest;
import com.test.EmployApp.dto.request.SearchEmployeeRequest;
import com.test.EmployApp.dto.request.UpdateEmployeeRequest;
import com.test.EmployApp.dto.response.EmployeeResponse;
import com.test.EmployApp.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeResponse createNew(NewEmployeeRequest request);
    Employee getById(String id);
    EmployeeResponse getOne(String id);
    Page<EmployeeResponse> getAll(SearchEmployeeRequest request);
    EmployeeResponse update(UpdateEmployeeRequest request);
    void deleteById(String id);
}

