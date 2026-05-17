package com.innovatech.analytics.service;

import com.innovatech.analytics.dto.*;
import com.innovatech.analytics.entity.AnalyticsMetric;
import com.innovatech.analytics.exception.ResourceNotFoundException;
import com.innovatech.analytics.factory.AnalyticsFactory;
import com.innovatech.analytics.repository.AnalyticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository repository;
    private final AnalyticsFactory factory;

    public List<AnalyticsResponseDTO> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AnalyticsResponseDTO findById(Long id) {
        return toResponse(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analytics metric not found: " + id)));
    }

    public AnalyticsResponseDTO create(AnalyticsRequestDTO dto) {
        validate(dto);
        return toResponse(repository.save(factory.fromRequest(dto)));
    }

    public AnalyticsResponseDTO update(Long id, AnalyticsRequestDTO dto) {
        validate(dto);
        AnalyticsMetric metric = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analytics metric not found: " + id));

        metric.setMetricName(dto.getMetricName());
        metric.setMetricType(dto.getMetricType());
        metric.setMetricValue(dto.getMetricValue());
        metric.setDescription(dto.getDescription());
        metric.setGeneratedDate(dto.getGeneratedDate());

        return toResponse(repository.save(metric));
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analytics metric not found: " + id)));
    }

    private void validate(AnalyticsRequestDTO dto) {
        if (dto.getMetricName() == null || dto.getMetricName().isBlank()) throw new IllegalArgumentException("metricName is required");
        if (dto.getMetricType() == null || dto.getMetricType().isBlank()) throw new IllegalArgumentException("metricType is required");
        if (dto.getMetricValue() == null) throw new IllegalArgumentException("metricValue is required");
    }

    private AnalyticsResponseDTO toResponse(AnalyticsMetric m) {
        return AnalyticsResponseDTO.builder()
                .id(m.getId())
                .metricName(m.getMetricName())
                .metricType(m.getMetricType())
                .metricValue(m.getMetricValue())
                .description(m.getDescription())
                .generatedDate(m.getGeneratedDate())
                .build();
    }
}
