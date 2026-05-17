package com.innovatech.projects.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String startDate;
    private String endDate;
    private Integer progress;
}
