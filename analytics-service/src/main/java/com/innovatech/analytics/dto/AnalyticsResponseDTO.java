package com.innovatech.analytics.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsResponseDTO {
    private Long id;
    private String metricName;
    private String metricType;
    private Double metricValue;
    private String description;
    private String generatedDate;
}
