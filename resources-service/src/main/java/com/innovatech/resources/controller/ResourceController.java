package com.innovatech.resources.controller;

import com.innovatech.resources.dto.*;
import com.innovatech.resources.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceService service;
    @GetMapping public List<ResourceResponseDTO> findAll(){ return service.findAll(); }
    @GetMapping("/{id}") public ResourceResponseDTO findById(@PathVariable Long id){ return service.findById(id); }
    @PostMapping public ResponseEntity<ResourceResponseDTO> create(@RequestBody ResourceRequestDTO dto){ return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PutMapping("/{id}") public ResourceResponseDTO update(@PathVariable Long id, @RequestBody ResourceRequestDTO dto){ return service.update(id, dto); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }
}
