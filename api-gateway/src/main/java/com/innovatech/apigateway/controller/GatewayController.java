package com.innovatech.apigateway.controller;

import com.innovatech.apigateway.dto.DashboardResponseDTO;
import com.innovatech.apigateway.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GatewayController {
    private final DashboardService service;

    @GetMapping({"/api/dashboard", "/dashboard"})
    public DashboardResponseDTO dashboard() {
        return service.getDashboard();
    }

    @GetMapping({"/api/projects", "/projects"})
    public ResponseEntity<Object> getProjects() {
        return service.proxyProjects(HttpMethod.GET, null, null);
    }

    @GetMapping({"/api/projects/{id}", "/projects/{id}"})
    public ResponseEntity<Object> getProject(@PathVariable Long id) {
        return service.proxyProjects(HttpMethod.GET, id, null);
    }

    @PostMapping({"/api/projects", "/projects"})
    public ResponseEntity<Object> createProject(@RequestBody Object body) {
        return service.proxyProjects(HttpMethod.POST, null, body);
    }

    @PutMapping({"/api/projects/{id}", "/projects/{id}"})
    public ResponseEntity<Object> updateProject(@PathVariable Long id, @RequestBody Object body) {
        return service.proxyProjects(HttpMethod.PUT, id, body);
    }

    @DeleteMapping({"/api/projects/{id}", "/projects/{id}"})
    public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
        return service.proxyProjects(HttpMethod.DELETE, id, null);
    }

    @GetMapping({"/api/resources", "/resources"})
    public ResponseEntity<Object> getResources() {
        return service.proxyResources(HttpMethod.GET, null, null);
    }

    @GetMapping({"/api/resources/{id}", "/resources/{id}"})
    public ResponseEntity<Object> getResource(@PathVariable Long id) {
        return service.proxyResources(HttpMethod.GET, id, null);
    }

    @PostMapping({"/api/resources", "/resources"})
    public ResponseEntity<Object> createResource(@RequestBody Object body) {
        return service.proxyResources(HttpMethod.POST, null, body);
    }

    @PutMapping({"/api/resources/{id}", "/resources/{id}"})
    public ResponseEntity<Object> updateResource(@PathVariable Long id, @RequestBody Object body) {
        return service.proxyResources(HttpMethod.PUT, id, body);
    }

    @DeleteMapping({"/api/resources/{id}", "/resources/{id}"})
    public ResponseEntity<Object> deleteResource(@PathVariable Long id) {
        return service.proxyResources(HttpMethod.DELETE, id, null);
    }

    @GetMapping({"/api/analytics", "/analytics"})
    public ResponseEntity<Object> getAnalytics() {
        return service.proxyAnalytics(HttpMethod.GET, null, null);
    }

    @GetMapping({"/api/analytics/{id}", "/analytics/{id}"})
    public ResponseEntity<Object> getMetric(@PathVariable Long id) {
        return service.proxyAnalytics(HttpMethod.GET, id, null);
    }

    @PostMapping({"/api/analytics", "/analytics"})
    public ResponseEntity<Object> createMetric(@RequestBody Object body) {
        return service.proxyAnalytics(HttpMethod.POST, null, body);
    }

    @PutMapping({"/api/analytics/{id}", "/analytics/{id}"})
    public ResponseEntity<Object> updateMetric(@PathVariable Long id, @RequestBody Object body) {
        return service.proxyAnalytics(HttpMethod.PUT, id, body);
    }

    @DeleteMapping({"/api/analytics/{id}", "/analytics/{id}"})
    public ResponseEntity<Object> deleteMetric(@PathVariable Long id) {
        return service.proxyAnalytics(HttpMethod.DELETE, id, null);
    }
}
