package com.innovatech.analytics.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "analytics_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String metricName;
    private String metricType;
    private Double metricValue;
    private String description;
    private String generatedDate;
}
