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
    @Mock ProjectRepository repository;
    @Mock ProjectFactory factory;
    @InjectMocks ProjectService service;

    private ProjectRequestDTO validRequest() {
        return ProjectRequestDTO.builder().name("Portal").description("Web").status("IN_PROGRESS").progress(50).build();
    }

    @Test void createProject() {
        var request = validRequest();
        var project = Project.builder().name("Portal").status("IN_PROGRESS").progress(50).build();
        when(factory.fromRequest(request)).thenReturn(project);
        when(repository.save(project)).thenReturn(Project.builder().id(1L).name("Portal").status("IN_PROGRESS").progress(50).build());
        assertEquals(1L, service.create(request).getId());
    }

    @Test void findAllAndFindById() {
        var project = Project.builder().id(1L).name("Portal").status("PLANNED").progress(0).build();
        when(repository.findAll()).thenReturn(List.of(project));
        when(repository.findById(1L)).thenReturn(Optional.of(project));
        assertEquals(1, service.findAll().size());
        assertEquals("Portal", service.findById(1L).getName());
    }

    @Test void updateProject() {
        var stored = Project.builder().id(1L).name("Old").status("PLANNED").progress(0).build();
        when(repository.findById(1L)).thenReturn(Optional.of(stored));
        when(repository.save(stored)).thenReturn(stored);
        var response = service.update(1L, validRequest());
        assertEquals("Portal", response.getName());
        assertEquals(50, response.getProgress());
    }

    @Test void deleteProject() {
        var stored = Project.builder().id(1L).status("PLANNED").progress(0).build();
        when(repository.findById(1L)).thenReturn(Optional.of(stored));
        service.delete(1L);
        verify(repository).delete(stored);
    }

    @Test void missingProjectThrows() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, validRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }

    @Test void validatesRequiredAndAllowedValues() {
        assertThrows(IllegalArgumentException.class, () -> service.create(ProjectRequestDTO.builder().status("PLANNED").progress(0).build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ProjectRequestDTO.builder().name("A").progress(0).build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ProjectRequestDTO.builder().name("A").status("UNKNOWN").progress(0).build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ProjectRequestDTO.builder().name("A").status("PLANNED").progress(-1).build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ProjectRequestDTO.builder().name("A").status("PLANNED").progress(101).build()));
    }
}
