package com.innovatech.apigateway.dto;

import lombok.Data;

@Data
public class AnalyticsDTO {
    private Long id;
    private String metricName;
    private String metricType;
    private Double metricValue;
    private String description;
    private String generatedDate;
}
