package com.innovatech.apigateway;

import com.innovatech.apigateway.dto.AnalyticsDTO;
import com.innovatech.apigateway.dto.ProjectDTO;
import com.innovatech.apigateway.dto.ResourceDTO;
import com.innovatech.apigateway.service.MicroservicesClient;
import com.innovatech.apigateway.service.ServiceCallResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MicroservicesClientTest {
    private RestTemplate restTemplate;
    private MicroservicesClient client;

    @BeforeEach void setUp() {
        restTemplate = mock(RestTemplate.class);
        client = new MicroservicesClient(restTemplate);
        ReflectionTestUtils.setField(client, "projectsUrl", "http://projects");
        ReflectionTestUtils.setField(client, "resourcesUrl", "http://resources");
        ReflectionTestUtils.setField(client, "analyticsUrl", "http://analytics");
    }

    @Test void retrievesListsFromAllServices() {
        doReturn(ResponseEntity.ok(List.of(new ProjectDTO())))
                .when(restTemplate).exchange(eq("http://projects"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
        doReturn(ResponseEntity.ok(List.of(new ResourceDTO())))
                .when(restTemplate).exchange(eq("http://resources"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
        doReturn(ResponseEntity.ok(List.of(new AnalyticsDTO())))
                .when(restTemplate).exchange(eq("http://analytics"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
        assertEquals(1, client.getProjects().data().size());
        assertEquals(1, client.getResources().data().size());
        assertEquals(1, client.getAnalytics().data().size());
    }

    @Test void convertsNullBodyToEmptyList() {
        doReturn(ResponseEntity.ok().build())
                .when(restTemplate).exchange(eq("http://projects"), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class));
        assertTrue(client.getProjects().data().isEmpty());
    }

    @Test void proxiesCrudAndBuildsIdUrl() {
        when(restTemplate.exchange(eq("http://projects/7"), eq(HttpMethod.PUT), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.ok("updated"));
        when(restTemplate.exchange(eq("http://resources"), eq(HttpMethod.POST), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body("created"));
        when(restTemplate.exchange(eq("http://analytics/3"), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(ResponseEntity.noContent().build());
        assertEquals("updated", client.proxyProjects(HttpMethod.PUT, 7L, "body").getBody());
        assertEquals(HttpStatus.CREATED, client.proxyResources(HttpMethod.POST, null, "body").getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, client.proxyAnalytics(HttpMethod.DELETE, 3L, null).getStatusCode());
    }

    @Test void fallbackMethodsReturnControlledResults() {
        ServiceCallResult<List<ProjectDTO>> projects = ReflectionTestUtils.invokeMethod(client, "projectsFallback", new RuntimeException());
        ServiceCallResult<List<ResourceDTO>> resources = ReflectionTestUtils.invokeMethod(client, "resourcesFallback", new RuntimeException());
        ServiceCallResult<List<AnalyticsDTO>> analytics = ReflectionTestUtils.invokeMethod(client, "analyticsFallback", new RuntimeException());
        ResponseEntity<Object> proxy = ReflectionTestUtils.invokeMethod(client, "proxyFallback", HttpMethod.GET, null, null, new RuntimeException());
        assertTrue(projects.fallbackUsed());
        assertTrue(resources.data().isEmpty());
        assertTrue(analytics.data().isEmpty());
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, proxy.getStatusCode());
    }
}
