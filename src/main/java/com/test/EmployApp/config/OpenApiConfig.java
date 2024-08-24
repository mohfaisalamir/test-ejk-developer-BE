package com.test.EmployApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "EmployApp API",
                version = "v1",
                description = "API documentation for EmployApp, a comprehensive platform for managing employee information and user authentication."
        ),
        tags = {
                @Tag(name = "Authentication", description = "\n Endpoints for authentication, including user registration, login, and token management. These endpoints handle user authentication, session management, and access control."),
                @Tag(name = "Employee", description = "\n Endpoints for employee management, including CRUD operations for employee records. These endpoints allow for creating, retrieving, updating, and deleting employee information, as well as searching and filtering employee data.")
        }
)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }
}
