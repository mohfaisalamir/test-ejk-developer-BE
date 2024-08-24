package com.test.EmployApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title("EmployApp API").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("employ-app-security"))
                .components(new Components()
                        .addSecuritySchemes("employ-app-security",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
//                .info(new Info().title("Your API Title").version("1.0"))
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
