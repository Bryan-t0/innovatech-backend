package com.innovatech.resources.factory;

import com.innovatech.resources.dto.ResourceRequestDTO;
import com.innovatech.resources.entity.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceFactory {
    public Resource fromRequest(ResourceRequestDTO dto){ return Resource.builder().fullName(dto.getFullName()).role(dto.getRole()).availability(dto.getAvailability()).email(dto.getEmail()).assignedProjectId(dto.getAssignedProjectId()).build(); }
}
