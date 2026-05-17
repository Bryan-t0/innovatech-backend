package com.innovatech.apigateway.service;

import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class DashboardServiceTest {
    private DashboardService service;

    @BeforeEach
    void setUp() {
        service = new DashboardService(new RestTemplate());
    }

    @Test
    void dashboardReturnsIntegratedTotals() {
        DashboardService spy = spy(service);
        ProjectDTO project = new ProjectDTO();
        project.setStatus("IN_PROGRESS");
        project.setProgress(50);
        ResourceDTO resource = new ResourceDTO();
        resource.setAvailability("AVAILABLE");

        doReturn(List.of(project)).when(spy).getProjectsWithFallback();
        doReturn(List.of(resource)).when(spy).getResourcesWithFallback();
        doReturn(List.of()).when(spy).getAnalyticsWithFallback();

        DashboardResponseDTO response = spy.getDashboard();

        assertEquals(1, response.getTotalProjects());
        assertEquals(1, response.getTotalResources());
        assertEquals(1, response.getActiveProjects());
        assertEquals(1, response.getAvailableResources());
        assertEquals(50.0, response.getAverageProgress());
    }

    @Test
    void dashboardKeepsWorkingWhenProjectsFail() {
        DashboardService spy = spy(service);
        ResourceDTO resource = new ResourceDTO();
        resource.setAvailability("AVAILABLE");

        doReturn(List.of()).when(spy).getProjectsWithFallback();
        doReturn(List.of(resource)).when(spy).getResourcesWithFallback();
        doReturn(List.of()).when(spy).getAnalyticsWithFallback();

        assertEquals(1, spy.getDashboard().getTotalResources());
    }

    @Test
    void dashboardKeepsWorkingWhenAnalyticsFail() {
        DashboardService spy = spy(service);

        doReturn(List.of()).when(spy).getProjectsWithFallback();
        doReturn(List.of()).when(spy).getResourcesWithFallback();
        doReturn(List.of()).when(spy).getAnalyticsWithFallback();

        assertNotNull(spy.getDashboard().getAnalytics());
    }
}
