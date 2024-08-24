package com.test.EmployApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEmployeeRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;


}
