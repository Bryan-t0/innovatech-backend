package com.innovatech.resources.service;

import com.innovatech.resources.dto.ResourceRequestDTO;
import com.innovatech.resources.entity.Resource;
import com.innovatech.resources.exception.ResourceNotFoundException;
import com.innovatech.resources.factory.ResourceFactory;
import com.innovatech.resources.repository.ResourceRepository;
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
class ResourceServiceTest {
    @Mock ResourceRepository repository;
    @Mock ResourceFactory factory;
    @InjectMocks ResourceService service;

    private ResourceRequestDTO validRequest() {
        return ResourceRequestDTO.builder().fullName("Ana").role("Developer").email("ana@innovatech.cl").availability("AVAILABLE").assignedProjectId(1L).build();
    }

    @Test void createResource() {
        var request = validRequest();
        var resource = Resource.builder().fullName("Ana").role("Developer").email("ana@innovatech.cl").availability("AVAILABLE").build();
        when(factory.fromRequest(request)).thenReturn(resource);
        when(repository.save(resource)).thenReturn(Resource.builder().id(1L).fullName("Ana").availability("AVAILABLE").build());
        assertEquals(1L, service.create(request).getId());
    }

    @Test void findAllAndFindById() {
        var resource = Resource.builder().id(1L).fullName("Ana").availability("AVAILABLE").build();
        when(repository.findAll()).thenReturn(List.of(resource));
        when(repository.findById(1L)).thenReturn(Optional.of(resource));
        assertEquals(1, service.findAll().size());
        assertEquals("Ana", service.findById(1L).getFullName());
    }

    @Test void updateAndDeleteResource() {
        var resource = Resource.builder().id(1L).availability("AVAILABLE").build();
        when(repository.findById(1L)).thenReturn(Optional.of(resource));
        when(repository.save(resource)).thenReturn(resource);
        assertEquals("Ana", service.update(1L, validRequest()).getFullName());
        service.delete(1L);
        verify(repository).delete(resource);
    }

    @Test void missingResourceThrows() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, validRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }

    @Test void validatesRequiredAndAllowedValues() {
        assertThrows(IllegalArgumentException.class, () -> service.create(ResourceRequestDTO.builder().build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ResourceRequestDTO.builder().fullName("A").build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ResourceRequestDTO.builder().fullName("A").role("Dev").build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ResourceRequestDTO.builder().fullName("A").role("Dev").email("a@b.cl").build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(ResourceRequestDTO.builder().fullName("A").role("Dev").email("a@b.cl").availability("UNKNOWN").build()));
    }
}
