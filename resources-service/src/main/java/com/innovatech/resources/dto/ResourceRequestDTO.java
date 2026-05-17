package com.innovatech.resources.dto;
import lombok.*;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceRequestDTO { private String fullName; private String role; private String availability; private String email; private Long assignedProjectId; }
