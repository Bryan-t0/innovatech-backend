package com.innovatech.apigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {
    private List<Object> projects;
    private List<Object> resources;
    private List<Object> analytics;
    private Integer totalProjects;
    private Integer totalResources;
    private Integer totalAnalytics;
    private Integer activeProjects;
    private Integer availableResources;
    private Double averageProgress;
    private String message;
}
