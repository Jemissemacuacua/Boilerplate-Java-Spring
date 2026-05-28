package com.app.builderplante.core.config;

import io.sentry.Sentry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SentryConfig {

    @Value("${sentry.dsn:}")
    private String sentryDsn;

    @PostConstruct
    public void init() {
        if (sentryDsn != null && !sentryDsn.isEmpty()) {
            log.info("Sentry inicializado com sucesso");
        } else {
            log.warn("Sentry DSN não configurado — erros não serão reportados");
        }
    }
}
