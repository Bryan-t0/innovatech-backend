package com.innovatech.apigateway.dto;

import lombok.Data;

@Data
public class ResourceDTO {
    private Long id;
    private String fullName;
    private String role;
    private String availability;
    private String email;
    private Long assignedProjectId;
}
