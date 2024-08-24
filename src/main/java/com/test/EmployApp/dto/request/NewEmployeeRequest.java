package com.test.EmployApp.dto.request;

import com.test.EmployApp.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEmployeeRequest {
    private String name;
    private String phoneNumber;
    private String userCredentialId;
}
