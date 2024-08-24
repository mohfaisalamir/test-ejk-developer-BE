package com.test.EmployApp.controller;

import com.test.EmployApp.dto.request.NewEmployeeRequest;
import com.test.EmployApp.dto.request.SearchEmployeeRequest;
import com.test.EmployApp.dto.request.UpdateEmployeeRequest;
import com.test.EmployApp.dto.response.CommonResponse;
import com.test.EmployApp.dto.response.EmployeeResponse;
import com.test.EmployApp.dto.response.PagingResponse;
import com.test.EmployApp.service.EmployeeService;
import com.test.EmployApp.util.PagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody NewEmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.createNew(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .message("successfully created new employee")
                .statusCode(HttpStatus.CREATED.value())
                .data(employeeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        EmployeeResponse employeeResponse = employeeService.getOne(id);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .message("successfully get employee by id")
                .statusCode(HttpStatus.OK.value())
                .data(employeeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @GetMapping
    public ResponseEntity<?> getAllEmployee(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    ) {
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);

        SearchEmployeeRequest request = SearchEmployeeRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<EmployeeResponse> employees = employeeService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(employees.getTotalElements())
                .totalPages(employees.getTotalPages())
                .build();

        CommonResponse<List<EmployeeResponse>> response = CommonResponse.<List<EmployeeResponse>>builder()
                .message("successfully get all employee")
                .statusCode(HttpStatus.OK.value())
                .data(employees.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse employeeResponse = employeeService.update(request);
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .message("successfully update employee")
                .statusCode(HttpStatus.OK.value())
                .data(employeeResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable String id) {
        employeeService.deleteById(id);
        CommonResponse<?> response = CommonResponse.builder()
                .message("successfully delete employee")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


}
