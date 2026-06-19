package com.innovatech.apigateway.service;

import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class DashboardServiceTest {
    private DashboardService service;
    private MicroservicesClient client;

    @BeforeEach
    void setUp() {
        client = mock(MicroservicesClient.class);
        service = new DashboardService(client);
    }

    @Test
    void dashboardReturnsIntegratedTotals() {
        ProjectDTO project = new ProjectDTO();
        project.setStatus("IN_PROGRESS");
        project.setProgress(50);
        ResourceDTO resource = new ResourceDTO();
        resource.setAvailability("AVAILABLE");

        doReturn(ServiceCallResult.success(List.of(project))).when(client).getProjects();
        doReturn(ServiceCallResult.success(List.of(resource))).when(client).getResources();
        doReturn(ServiceCallResult.success(List.of())).when(client).getAnalytics();

        DashboardResponseDTO response = service.getDashboard();

        assertEquals(1, response.getTotalProjects());
        assertEquals(1, response.getTotalResources());
        assertEquals(1, response.getActiveProjects());
        assertEquals(1, response.getAvailableResources());
        assertEquals(50.0, response.getAverageProgress());
    }

    @Test
    void dashboardKeepsWorkingWhenProjectsFail() {
        ResourceDTO resource = new ResourceDTO();
        resource.setAvailability("AVAILABLE");

        doReturn(ServiceCallResult.fallback(List.of())).when(client).getProjects();
        doReturn(ServiceCallResult.success(List.of(resource))).when(client).getResources();
        doReturn(ServiceCallResult.success(List.of())).when(client).getAnalytics();

        DashboardResponseDTO response = service.getDashboard();
        assertEquals(1, response.getTotalResources());
        assertEquals("No fue posible obtener informacion desde uno de los microservicios.", response.getMessage());
    }

    @Test
    void dashboardKeepsWorkingWhenAnalyticsFail() {
        doReturn(ServiceCallResult.success(List.of())).when(client).getProjects();
        doReturn(ServiceCallResult.success(List.of())).when(client).getResources();
        doReturn(ServiceCallResult.fallback(List.of())).when(client).getAnalytics();

        assertNotNull(service.getDashboard().getAnalytics());
    }
}
