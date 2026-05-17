package com.innovatech.projects.controller;

import com.innovatech.projects.dto.*;
import com.innovatech.projects.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService service;
    @GetMapping public List<ProjectResponseDTO> findAll(){ return service.findAll(); }
    @GetMapping("/{id}") public ProjectResponseDTO findById(@PathVariable Long id){ return service.findById(id); }
    @PostMapping public ResponseEntity<ProjectResponseDTO> create(@RequestBody ProjectRequestDTO dto){ return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PutMapping("/{id}") public ProjectResponseDTO update(@PathVariable Long id, @RequestBody ProjectRequestDTO dto){ return service.update(id, dto); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }
}
