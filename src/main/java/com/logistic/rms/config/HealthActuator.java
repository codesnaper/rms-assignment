package com.logistic.rms.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Health Config
 */
@Component
public class HealthActuator implements HealthIndicator {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Health health() {
        return new Health.Builder().up().build();
    }
}