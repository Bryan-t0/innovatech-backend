package com.innovatech.analytics.service;

import com.innovatech.analytics.dto.AnalyticsRequestDTO;
import com.innovatech.analytics.entity.AnalyticsMetric;
import com.innovatech.analytics.exception.ResourceNotFoundException;
import com.innovatech.analytics.factory.AnalyticsFactory;
import com.innovatech.analytics.repository.AnalyticsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {
    @Mock private AnalyticsRepository repository;
    @Mock private AnalyticsFactory factory;
    @InjectMocks private AnalyticsService service;

    @Test void createMetric() {
        AnalyticsRequestDTO req = AnalyticsRequestDTO.builder().metricName("KPI").metricType("PROJECT_PROGRESS").metricValue(80.0).build();
        AnalyticsMetric metric = AnalyticsMetric.builder().metricName("KPI").metricType("PROJECT_PROGRESS").metricValue(80.0).build();
        when(factory.fromRequest(req)).thenReturn(metric);
        when(repository.save(metric)).thenReturn(AnalyticsMetric.builder().id(1L).metricName("KPI").metricType("PROJECT_PROGRESS").metricValue(80.0).build());

        assertEquals(1L, service.create(req).getId());
    }

    @Test void listMetrics() {
        when(repository.findAll()).thenReturn(List.of(AnalyticsMetric.builder().id(1L).metricName("A").metricType("T").metricValue(1.0).build()));
        assertEquals(1, service.findAll().size());
    }

    @Test void findMetricExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(AnalyticsMetric.builder().id(1L).metricName("A").metricType("T").metricValue(1.0).build()));
        assertEquals(1L, service.findById(1L).getId());
    }

    @Test void findMetricNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }
}
