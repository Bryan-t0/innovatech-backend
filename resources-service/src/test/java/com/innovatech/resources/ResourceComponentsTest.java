package com.innovatech.resources;

import com.innovatech.resources.controller.ResourceController;
import com.innovatech.resources.dto.ResourceRequestDTO;
import com.innovatech.resources.dto.ResourceResponseDTO;
import com.innovatech.resources.exception.GlobalExceptionHandler;
import com.innovatech.resources.exception.ResourceNotFoundException;
import com.innovatech.resources.factory.ResourceFactory;
import com.innovatech.resources.service.ResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceComponentsTest {
    @Test void factoryMapsRequest() {
        var dto = ResourceRequestDTO.builder().fullName("Ana").role("Dev").email("a@b.cl").availability("AVAILABLE").assignedProjectId(2L).build();
        var entity = new ResourceFactory().fromRequest(dto);
        assertEquals("Ana", entity.getFullName());
        assertEquals(2L, entity.getAssignedProjectId());
    }

    @Test void controllerDelegatesCrud() {
        var service = mock(ResourceService.class);
        var controller = new ResourceController(service);
        var request = ResourceRequestDTO.builder().build();
        var response = ResourceResponseDTO.builder().id(1L).build();
        when(service.findAll()).thenReturn(List.of(response));
        when(service.findById(1L)).thenReturn(response);
        when(service.create(request)).thenReturn(response);
        when(service.update(1L, request)).thenReturn(response);
        assertEquals(1, controller.findAll().size());
        assertEquals(1L, controller.findById(1L).getId());
        assertEquals(HttpStatus.CREATED, controller.create(request).getStatusCode());
        assertEquals(1L, controller.update(1L, request).getId());
        assertEquals(HttpStatus.NO_CONTENT, controller.delete(1L).getStatusCode());
    }

    @Test void handlerReturnsControlledErrors() {
        var handler = new GlobalExceptionHandler();
        assertEquals(HttpStatus.NOT_FOUND, handler.handleNotFound(new ResourceNotFoundException("missing")).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, handler.handleValidation(new IllegalArgumentException("invalid")).getStatusCode());
    }
}
