package com.innovatech.analytics;

import com.innovatech.analytics.controller.AnalyticsController;
import com.innovatech.analytics.dto.AnalyticsRequestDTO;
import com.innovatech.analytics.dto.AnalyticsResponseDTO;
import com.innovatech.analytics.exception.GlobalExceptionHandler;
import com.innovatech.analytics.exception.ResourceNotFoundException;
import com.innovatech.analytics.factory.AnalyticsFactory;
import com.innovatech.analytics.service.AnalyticsService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyticsComponentsTest {
    @Test void factoryMapsRequest() {
        var dto = AnalyticsRequestDTO.builder().metricName("KPI").metricType("QUALITY").metricValue(90.0).description("Calidad").build();
        var entity = new AnalyticsFactory().fromRequest(dto);
        assertEquals("KPI", entity.getMetricName());
        assertEquals(90.0, entity.getMetricValue());
    }

    @Test void controllerDelegatesCrud() {
        var service = mock(AnalyticsService.class);
        var controller = new AnalyticsController(service);
        var request = AnalyticsRequestDTO.builder().build();
        var response = AnalyticsResponseDTO.builder().id(1L).build();
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
