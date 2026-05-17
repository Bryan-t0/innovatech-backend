package com.innovatech.analytics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsRequestDTO {
    private String metricName;
    private String metricType;
    private Double metricValue;
    private String description;
    private String generatedDate;
}
