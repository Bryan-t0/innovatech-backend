package com.innovatech.analytics.service;

import com.innovatech.analytics.dto.AnalyticsRequestDTO;
import com.innovatech.analytics.entity.AnalyticsMetric;
import com.innovatech.analytics.exception.ResourceNotFoundException;
import com.innovatech.analytics.factory.AnalyticsFactory;
import com.innovatech.analytics.repository.AnalyticsRepository;
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
class AnalyticsServiceTest {
    @Mock AnalyticsRepository repository;
    @Mock AnalyticsFactory factory;
    @InjectMocks AnalyticsService service;

    private AnalyticsRequestDTO validRequest() {
        return AnalyticsRequestDTO.builder().metricName("Avance").metricType("PROJECT_PROGRESS").metricValue(80.0).description("KPI").build();
    }

    @Test void createMetric() {
        var request = validRequest();
        var metric = AnalyticsMetric.builder().metricName("Avance").metricType("PROJECT_PROGRESS").metricValue(80.0).build();
        when(factory.fromRequest(request)).thenReturn(metric);
        when(repository.save(metric)).thenReturn(AnalyticsMetric.builder().id(1L).metricName("Avance").metricType("PROJECT_PROGRESS").metricValue(80.0).build());
        assertEquals(1L, service.create(request).getId());
    }

    @Test void findAllAndFindById() {
        var metric = AnalyticsMetric.builder().id(1L).metricName("A").metricType("T").metricValue(1.0).build();
        when(repository.findAll()).thenReturn(List.of(metric));
        when(repository.findById(1L)).thenReturn(Optional.of(metric));
        assertEquals(1, service.findAll().size());
        assertEquals("A", service.findById(1L).getMetricName());
    }

    @Test void updateAndDeleteMetric() {
        var metric = AnalyticsMetric.builder().id(1L).metricName("Old").metricType("OLD").metricValue(1.0).build();
        when(repository.findById(1L)).thenReturn(Optional.of(metric));
        when(repository.save(metric)).thenReturn(metric);
        assertEquals("Avance", service.update(1L, validRequest()).getMetricName());
        service.delete(1L);
        verify(repository).delete(metric);
    }

    @Test void missingMetricThrows() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, validRequest()));
        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }

    @Test void validatesRequiredValues() {
        assertThrows(IllegalArgumentException.class, () -> service.create(AnalyticsRequestDTO.builder().build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(AnalyticsRequestDTO.builder().metricName("A").build()));
        assertThrows(IllegalArgumentException.class, () -> service.create(AnalyticsRequestDTO.builder().metricName("A").metricType("T").build()));
    }
}
