package com.innovatech.resources.service;

import com.innovatech.resources.dto.ResourceRequestDTO;
import com.innovatech.resources.dto.ResourceResponseDTO;
import com.innovatech.resources.entity.Resource;
import com.innovatech.resources.exception.ResourceNotFoundException;
import com.innovatech.resources.factory.ResourceFactory;
import com.innovatech.resources.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository repository;
    private final ResourceFactory factory;

    public List<ResourceResponseDTO> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ResourceResponseDTO findById(Long id) {
        Resource resource = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + id));
        return toResponse(resource);
    }

    public ResourceResponseDTO create(ResourceRequestDTO dto) {
        validate(dto);
        Resource resource = factory.fromRequest(dto);
        return toResponse(repository.save(resource));
    }

    public ResourceResponseDTO update(Long id, ResourceRequestDTO dto) {
        validate(dto);
        Resource resource = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + id));

        resource.setFullName(dto.getFullName());
        resource.setRole(dto.getRole());
        resource.setAvailability(dto.getAvailability());
        resource.setEmail(dto.getEmail());
        resource.setAssignedProjectId(dto.getAssignedProjectId());

        return toResponse(repository.save(resource));
    }

    public void delete(Long id) {
        Resource resource = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + id));
        repository.delete(resource);
    }

    private void validate(ResourceRequestDTO dto) {
        if (dto.getFullName() == null || dto.getFullName().isBlank()) {
            throw new IllegalArgumentException("fullName is required");
        }
        if (dto.getRole() == null || dto.getRole().isBlank()) {
            throw new IllegalArgumentException("role is required");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (dto.getAvailability() == null || dto.getAvailability().isBlank()) {
            throw new IllegalArgumentException("availability is required");
        }
        if (!List.of("AVAILABLE", "ASSIGNED", "UNAVAILABLE").contains(dto.getAvailability())) {
            throw new IllegalArgumentException("Invalid availability");
        }
    }

    private ResourceResponseDTO toResponse(Resource resource) {
        return ResourceResponseDTO.builder()
                .id(resource.getId())
                .fullName(resource.getFullName())
                .role(resource.getRole())
                .availability(resource.getAvailability())
                .email(resource.getEmail())
                .assignedProjectId(resource.getAssignedProjectId())
                .build();
    }
}