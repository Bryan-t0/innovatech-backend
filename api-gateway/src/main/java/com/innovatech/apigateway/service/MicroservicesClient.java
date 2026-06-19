package com.innovatech.apigateway.service;

import com.innovatech.apigateway.dto.AnalyticsDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MicroservicesClient {
    private final RestTemplate restTemplate;

    @Value("${services.projects.url}")
    private String projectsUrl;

    @Value("${services.resources.url}")
    private String resourcesUrl;

    @Value("${services.analytics.url}")
    private String analyticsUrl;

    @CircuitBreaker(name = "projectsService", fallbackMethod = "projectsFallback")
    public ServiceCallResult<List<ProjectDTO>> getProjects() {
        return ServiceCallResult.success(getList(projectsUrl, new ParameterizedTypeReference<>() {}));
    }

    @CircuitBreaker(name = "resourcesService", fallbackMethod = "resourcesFallback")
    public ServiceCallResult<List<ResourceDTO>> getResources() {
        return ServiceCallResult.success(getList(resourcesUrl, new ParameterizedTypeReference<>() {}));
    }

    @CircuitBreaker(name = "analyticsService", fallbackMethod = "analyticsFallback")
    public ServiceCallResult<List<AnalyticsDTO>> getAnalytics() {
        return ServiceCallResult.success(getList(analyticsUrl, new ParameterizedTypeReference<>() {}));
    }

    @CircuitBreaker(name = "projectsService", fallbackMethod = "proxyFallback")
    public ResponseEntity<Object> proxyProjects(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(projectsUrl, id), method, body);
    }

    @CircuitBreaker(name = "resourcesService", fallbackMethod = "proxyFallback")
    public ResponseEntity<Object> proxyResources(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(resourcesUrl, id), method, body);
    }

    @CircuitBreaker(name = "analyticsService", fallbackMethod = "proxyFallback")
    public ResponseEntity<Object> proxyAnalytics(HttpMethod method, Long id, Object body) {
        return proxy(buildUrl(analyticsUrl, id), method, body);
    }

    private <T> List<T> getList(String url, ParameterizedTypeReference<List<T>> responseType) {
        List<T> body = restTemplate.exchange(url, HttpMethod.GET, null, responseType).getBody();
        return body == null ? List.of() : body;
    }

    private ResponseEntity<Object> proxy(String url, HttpMethod method, Object body) {
        return restTemplate.exchange(url, method, new HttpEntity<>(body), Object.class);
    }

    private String buildUrl(String baseUrl, Long id) {
        return id == null ? baseUrl : baseUrl + "/" + id;
    }

    private ServiceCallResult<List<ProjectDTO>> projectsFallback(Throwable exception) {
        return ServiceCallResult.fallback(List.of());
    }

    private ServiceCallResult<List<ResourceDTO>> resourcesFallback(Throwable exception) {
        return ServiceCallResult.fallback(List.of());
    }

    private ServiceCallResult<List<AnalyticsDTO>> analyticsFallback(Throwable exception) {
        return ServiceCallResult.fallback(List.of());
    }

    private ResponseEntity<Object> proxyFallback(HttpMethod method, Long id, Object body, Throwable exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "message", "El microservicio no esta disponible en este momento.",
                "reason", exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage()
        ));
    }
}
