package com.innovatech.projects.service;

import com.innovatech.projects.dto.ProjectRequestDTO;
import com.innovatech.projects.dto.ProjectResponseDTO;
import com.innovatech.projects.entity.Project;
import com.innovatech.projects.exception.ResourceNotFoundException;
import com.innovatech.projects.factory.ProjectFactory;
import com.innovatech.projects.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final ProjectFactory factory;

    public List<ProjectResponseDTO> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProjectResponseDTO findById(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
        return toResponse(project);
    }

    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        validate(dto);
        Project project = factory.fromRequest(dto);
        return toResponse(repository.save(project));
    }

    public ProjectResponseDTO update(Long id, ProjectRequestDTO dto) {
        validate(dto);
        Project project = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStatus(dto.getStatus());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setProgress(dto.getProgress());

        return toResponse(repository.save(project));
    }

    public void delete(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
        repository.delete(project);
    }

    private void validate(ProjectRequestDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new IllegalArgumentException("status is required");
        }
        if (!List.of("PLANNED", "IN_PROGRESS", "COMPLETED", "PAUSED").contains(dto.getStatus())) {
            throw new IllegalArgumentException("Invalid status");
        }
        if (dto.getProgress() == null || dto.getProgress() < 0 || dto.getProgress() > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100");
        }
    }

    private ProjectResponseDTO toResponse(Project project) {
        return ProjectResponseDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .progress(project.getProgress())
                .build();
    }
}