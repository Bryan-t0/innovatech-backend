package com.innovatech.projects.factory;

import com.innovatech.projects.dto.ProjectRequestDTO;
import com.innovatech.projects.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectFactory {
    public Project fromRequest(ProjectRequestDTO dto) {
        return Project.builder().name(dto.getName()).description(dto.getDescription()).status(dto.getStatus())
                .startDate(dto.getStartDate()).endDate(dto.getEndDate()).progress(dto.getProgress()).build();
    }
}
