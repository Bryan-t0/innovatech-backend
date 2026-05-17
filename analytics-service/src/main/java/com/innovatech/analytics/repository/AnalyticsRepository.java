package com.innovatech.analytics.repository;

import com.innovatech.analytics.entity.AnalyticsMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<AnalyticsMetric, Long> {
}
