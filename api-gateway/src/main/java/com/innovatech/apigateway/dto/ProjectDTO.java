package com.innovatech.apigateway.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String startDate;
    private String endDate;
    private Integer progress;
}
