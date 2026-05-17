package com.innovatech.projects.service;

import com.innovatech.projects.dto.ProjectRequestDTO;
import com.innovatech.projects.entity.Project;
import com.innovatech.projects.exception.ResourceNotFoundException;
import com.innovatech.projects.factory.ProjectFactory;
import com.innovatech.projects.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock private ProjectRepository repository;
    @Mock private ProjectFactory factory;
    @InjectMocks private ProjectService service;

    @Test void createProject(){
        ProjectRequestDTO req = ProjectRequestDTO.builder().name("A").status("PLANNED").progress(0).build();
        Project p = Project.builder().name("A").status("PLANNED").progress(0).build();
        when(factory.fromRequest(req)).thenReturn(p); when(repository.save(p)).thenReturn(Project.builder().id(1L).name("A").status("PLANNED").progress(0).build());
        assertEquals(1L, service.create(req).getId());
    }
    @Test void findAllProjects(){ when(repository.findAll()).thenReturn(List.of(Project.builder().id(1L).status("PLANNED").progress(0).build())); assertEquals(1, service.findAll().size()); }
    @Test void findByIdWhenExists(){ when(repository.findById(1L)).thenReturn(Optional.of(Project.builder().id(1L).status("PLANNED").progress(0).build())); assertEquals(1L, service.findById(1L).getId()); }
    @Test void findByIdWhenNotExists(){ when(repository.findById(99L)).thenReturn(Optional.empty()); assertThrows(ResourceNotFoundException.class, () -> service.findById(99L)); }
}
