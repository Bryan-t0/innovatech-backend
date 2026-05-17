package com.innovatech.resources.service;

import com.innovatech.resources.dto.ResourceRequestDTO;
import com.innovatech.resources.entity.Resource;
import com.innovatech.resources.exception.ResourceNotFoundException;
import com.innovatech.resources.factory.ResourceFactory;
import com.innovatech.resources.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {
    @Mock private ResourceRepository repository;
    @Mock private ResourceFactory factory;
    @InjectMocks private ResourceService service;

    @Test void createResource(){
        ResourceRequestDTO req = ResourceRequestDTO.builder().fullName("A").role("Developer").email("a@innovatech.cl").availability("AVAILABLE").build();
        Resource resource = Resource.builder().fullName("A").role("Developer").email("a@innovatech.cl").availability("AVAILABLE").build();
        when(factory.fromRequest(req)).thenReturn(resource); when(repository.save(resource)).thenReturn(Resource.builder().id(1L).availability("AVAILABLE").build());
        assertEquals(1L, service.create(req).getId());
    }
    @Test void findAllResources(){ when(repository.findAll()).thenReturn(List.of(Resource.builder().id(1L).availability("AVAILABLE").build())); assertEquals(1, service.findAll().size()); }
    @Test void findByIdWhenExists(){ when(repository.findById(1L)).thenReturn(Optional.of(Resource.builder().id(1L).availability("AVAILABLE").build())); assertEquals(1L, service.findById(1L).getId()); }
    @Test void findByIdWhenNotExists(){ when(repository.findById(88L)).thenReturn(Optional.empty()); assertThrows(ResourceNotFoundException.class, () -> service.findById(88L)); }
}
