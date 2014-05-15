package com.hackerbetter.artist.servlet;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.AdminServletContextListener;

public class MetricsAdminServletContextListener extends
        AdminServletContextListener {

    public static final MetricRegistry REGISTRY = new MetricRegistry();
    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();
    
    @Override
    protected MetricRegistry getMetricRegistry() {
        return REGISTRY;
    }

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }

}
