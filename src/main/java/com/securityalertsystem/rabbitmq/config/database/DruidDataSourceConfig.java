package com.securityalertsystem.rabbitmq.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix)
public class DruidDataSourceConfig {
}
