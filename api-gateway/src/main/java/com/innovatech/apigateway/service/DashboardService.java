package com.innovatech.apigateway.service;

import com.innovatech.apigateway.dto.AnalyticsDTO;
import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private static final String FALLBACK_MESSAGE = "No fue posible obtener informacion desde uno de los microservicios.";

    private final MicroservicesClient microservicesClient;

    public DashboardResponseDTO getDashboard() {
        ServiceCallResult<List<ProjectDTO>> projectsResult = microservicesClient.getProjects();
        ServiceCallResult<List<ResourceDTO>> resourcesResult = microservicesClient.getResources();
        ServiceCallResult<List<AnalyticsDTO>> analyticsResult = microservicesClient.getAnalytics();
        List<ProjectDTO> projects = projectsResult.data();
        List<ResourceDTO> resources = resourcesResult.data();
        List<AnalyticsDTO> analytics = analyticsResult.data();
        boolean fallbackUsed = projectsResult.fallbackUsed()
                || resourcesResult.fallbackUsed()
                || analyticsResult.fallbackUsed();

        int activeProjects = (int) projects.stream()
                .filter(project -> "IN_PROGRESS".equals(project.getStatus()))
                .count();
        int availableResources = (int) resources.stream()
                .filter(resource -> "AVAILABLE".equals(resource.getAvailability()))
                .count();
        double averageProgress = projects.stream()
                .map(ProjectDTO::getProgress)
                .filter(progress -> progress != null)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return DashboardResponseDTO.builder()
                .projects(new ArrayList<>(projects))
                .resources(new ArrayList<>(resources))
                .analytics(new ArrayList<>(analytics))
                .totalProjects(projects.size())
                .totalResources(resources.size())
                .totalAnalytics(analytics.size())
                .activeProjects(activeProjects)
                .availableResources(availableResources)
                .averageProgress(averageProgress)
                .message(fallbackUsed ? FALLBACK_MESSAGE : null)
                .build();
    }

    public ResponseEntity<Object> proxyProjects(HttpMethod method, Long id, Object body) {
        return microservicesClient.proxyProjects(method, id, body);
    }

    public ResponseEntity<Object> proxyResources(HttpMethod method, Long id, Object body) {
        return microservicesClient.proxyResources(method, id, body);
    }

    public ResponseEntity<Object> proxyAnalytics(HttpMethod method, Long id, Object body) {
        return microservicesClient.proxyAnalytics(method, id, body);
    }
}
