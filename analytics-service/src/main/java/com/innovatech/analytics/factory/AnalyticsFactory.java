package com.innovatech.analytics.factory;

import com.innovatech.analytics.dto.AnalyticsRequestDTO;
import com.innovatech.analytics.entity.AnalyticsMetric;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsFactory {
    public AnalyticsMetric fromRequest(AnalyticsRequestDTO dto) {
        return AnalyticsMetric.builder()
                .metricName(dto.getMetricName())
                .metricType(dto.getMetricType())
                .metricValue(dto.getMetricValue())
                .description(dto.getDescription())
                .generatedDate(dto.getGeneratedDate())
                .build();
    }
}
