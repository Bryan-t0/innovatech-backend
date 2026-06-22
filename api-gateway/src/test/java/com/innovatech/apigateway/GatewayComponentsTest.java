package com.innovatech.apigateway;

import com.innovatech.apigateway.controller.GatewayController;
import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.exception.GlobalExceptionHandler;
import com.innovatech.apigateway.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GatewayComponentsTest {
    @Test void controllerDelegatesAllRoutes() {
        var service = mock(DashboardService.class);
        var controller = new GatewayController(service);
        var ok = ResponseEntity.ok((Object) "ok");
        when(service.getDashboard()).thenReturn(DashboardResponseDTO.builder().totalProjects(1).build());
        when(service.proxyProjects(any(), any(), any())).thenReturn(ok);
        when(service.proxyResources(any(), any(), any())).thenReturn(ok);
        when(service.proxyAnalytics(any(), any(), any())).thenReturn(ok);

        assertEquals(1, controller.dashboard().getTotalProjects());
        assertSame(ok, controller.getProjects());
        assertSame(ok, controller.getProject(1L));
        assertSame(ok, controller.createProject("body"));
        assertSame(ok, controller.updateProject(1L, "body"));
        assertSame(ok, controller.deleteProject(1L));
        assertSame(ok, controller.getResources());
        assertSame(ok, controller.getResource(1L));
        assertSame(ok, controller.createResource("body"));
        assertSame(ok, controller.updateResource(1L, "body"));
        assertSame(ok, controller.deleteResource(1L));
        assertSame(ok, controller.getAnalytics());
        assertSame(ok, controller.getMetric(1L));
        assertSame(ok, controller.createMetric("body"));
        assertSame(ok, controller.updateMetric(1L, "body"));
        assertSame(ok, controller.deleteMetric(1L));
        verify(service).proxyProjects(HttpMethod.GET, null, null);
    }

    @Test void exceptionHandlerReturnsServiceUnavailable() {
        var response = new GlobalExceptionHandler().handleAny(new RuntimeException("failure"));
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertTrue(response.getBody().get("message").contains("microservicios"));
    }
}
