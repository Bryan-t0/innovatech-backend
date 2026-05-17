package com.innovatech.projects.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDTO {
    private String name;
    private String description;
    private String status;
    private String startDate;
    private String endDate;
    private Integer progress;
}
