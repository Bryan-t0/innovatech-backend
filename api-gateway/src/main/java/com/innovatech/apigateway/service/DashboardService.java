package com.innovatech.apigateway.service;

import com.innovatech.apigateway.dto.AnalyticsDTO;
import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private static final String FALLBACK_MESSAGE = "No fue posible obtener informacion desde uno de los microservicios.";

    private final RestTemplate restTemplate;
    private boolean fallbackUsed;

    @Value("${services.projects.url}")
    private String projectsUrl;

    @Value("${services.resources.url}")
    private String resourcesUrl;

    @Value("${services.analytics.url}")
    private String analyticsUrl;

    public DashboardResponseDTO getDashboard() {
        fallbackUsed = false;
        List<ProjectDTO> projects = getProjectsWithFallback();
        List<ResourceDTO> resources = getResourcesWithFallback();
        List<AnalyticsDTO> analytics = getAnalyticsWithFallback();

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

    public List<ProjectDTO> getProjectsWithFallback() {
        try {
            List<ProjectDTO> body = restTemplate.exchange(projectsUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<ProjectDTO>>() {}).getBody();
            return body == null ? List.of() : body;
        } catch (Exception ex) {
            fallbackUsed = true;
            return List.of();
        }
    }

    public List<ResourceDTO> getResourcesWithFallback() {
        try {
            List<ResourceDTO> body = restTemplate.exchange(resourcesUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<ResourceDTO>>() {}).getBody();
            return body == null ? List.of() : body;
        } catch (Exception ex) {
            fallbackUsed = true;
            return List.of();
        }
    }

    public List<AnalyticsDTO> getAnalyticsWithFallback() {
        try {
            List<AnalyticsDTO> body = restTemplate.exchange(analyticsUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<AnalyticsDTO>>() {}).getBody();
            return body == null ? List.of() : body;
        } catch (Exception ex) {
            fallbackUsed = true;
            return List.of();
        }
    }

    public ResponseEntity<Object> proxyProjects(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(projectsUrl, id), method, body);
    }

    public ResponseEntity<Object> proxyResources(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(resourcesUrl, id), method, body);
    }

    public ResponseEntity<Object> proxyAnalytics(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(analyticsUrl, id), method, body);
    }

    private ResponseEntity<Object> proxy(String url, HttpMethod method, Object body) {
        return restTemplate.exchange(url, method, new HttpEntity<>(body), Object.class);
    }

    private String buildUrl(String baseUrl, Long id) {
        return id == null ? baseUrl : baseUrl + "/" + id;
    }
}
