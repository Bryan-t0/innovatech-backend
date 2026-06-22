package com.innovatech.projects;

import com.innovatech.projects.controller.ProjectController;
import com.innovatech.projects.dto.ProjectRequestDTO;
import com.innovatech.projects.dto.ProjectResponseDTO;
import com.innovatech.projects.exception.GlobalExceptionHandler;
import com.innovatech.projects.exception.ResourceNotFoundException;
import com.innovatech.projects.factory.ProjectFactory;
import com.innovatech.projects.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectComponentsTest {
    @Test void factoryMapsRequest() {
        var dto = ProjectRequestDTO.builder().name("A").description("D").status("PLANNED").progress(10).build();
        var entity = new ProjectFactory().fromRequest(dto);
        assertEquals("A", entity.getName());
        assertEquals(10, entity.getProgress());
    }

    @Test void controllerDelegatesCrud() {
        var service = mock(ProjectService.class);
        var controller = new ProjectController(service);
        var request = ProjectRequestDTO.builder().name("A").status("PLANNED").progress(0).build();
        var response = ProjectResponseDTO.builder().id(1L).build();
        when(service.findAll()).thenReturn(List.of(response));
        when(service.findById(1L)).thenReturn(response);
        when(service.create(request)).thenReturn(response);
        when(service.update(1L, request)).thenReturn(response);
        assertEquals(1, controller.findAll().size());
        assertEquals(1L, controller.findById(1L).getId());
        assertEquals(HttpStatus.CREATED, controller.create(request).getStatusCode());
        assertEquals(1L, controller.update(1L, request).getId());
        assertEquals(HttpStatus.NO_CONTENT, controller.delete(1L).getStatusCode());
        verify(service).delete(1L);
    }

    @Test void handlerReturnsControlledErrors() {
        var handler = new GlobalExceptionHandler();
        var notFound = handler.handleNotFound(new ResourceNotFoundException("missing"));
        var invalid = handler.handleValidation(new IllegalArgumentException("invalid"));
        assertEquals(HttpStatus.NOT_FOUND, notFound.getStatusCode());
        assertEquals("missing", notFound.getBody().get("message"));
        assertEquals(HttpStatus.BAD_REQUEST, invalid.getStatusCode());
    }
}
