package com.innovatech.analytics.controller;

import com.innovatech.analytics.dto.*;
import com.innovatech.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService service;

    @GetMapping
    public List<AnalyticsResponseDTO> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public AnalyticsResponseDTO findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<AnalyticsResponseDTO> create(@RequestBody AnalyticsRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    public AnalyticsResponseDTO update(@PathVariable Long id, @RequestBody AnalyticsRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
