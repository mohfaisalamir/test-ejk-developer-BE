package com.test.EmployApp.service.impl;

import com.test.EmployApp.dto.request.NewEmployeeRequest;
import com.test.EmployApp.dto.request.SearchEmployeeRequest;
import com.test.EmployApp.dto.request.UpdateEmployeeRequest;
import com.test.EmployApp.dto.response.EmployeeResponse;
import com.test.EmployApp.entity.Employee;
import com.test.EmployApp.entity.UserCredential;
import com.test.EmployApp.repository.EmployeeRepository;
import com.test.EmployApp.service.EmployeeService;
import com.test.EmployApp.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse createNew(NewEmployeeRequest request) {
        try {
            UserCredential userCredential = UserCredential.builder().
                    id(request.getUserCredentialId())
                    .build();
            Employee employee = Employee.builder()
                    .name(request.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .userCredential(userCredential)
                    .build();
            employeeRepository.saveAndFlush(employee);
            return mapToResponse(employee);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }

    }
    @Transactional(readOnly = true)
    @Override
    public Employee getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public EmployeeResponse getOne(String id) {
        Employee employee = findByIdOrThrowNotFound(id);
        return mapToResponse(employee);

    }

    @Override
    public Page<EmployeeResponse> getAll(SearchEmployeeRequest request) {
        for (Field declaredField : Employee.class.getDeclaredFields()) {
            if (!declaredField.getName().equals(request.getSortBy())) {
                request.setSortBy("name");
            }
        }

        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getSize(),
                direction,
                request.getSortBy()
        );

        Page<Employee> employees = employeeRepository.findAll(pageable);
        return employees.map(employee -> mapToResponse(employee));

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse update(UpdateEmployeeRequest request) {
        try {
            validationUtil.validate(request);
            Employee currentEmployee = findByIdOrThrowNotFound(request.getId());
            currentEmployee.setName(request.getName());
            currentEmployee.setPhoneNumber(request.getPhoneNumber());
            employeeRepository.saveAndFlush(currentEmployee);
            return mapToResponse(currentEmployee);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "phone number already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        Employee employee = findByIdOrThrowNotFound(id);
        employeeRepository.delete(employee);
    }


    private Employee findByIdOrThrowNotFound(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "employee not found"));
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getId())
                .name(employee.getName())
                .phoneNumber(employee.getPhoneNumber())
                .build();
    }

}
