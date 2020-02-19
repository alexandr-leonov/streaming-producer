package com.streaming.producer.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.security.user")
public class SecurityProperties {

    private String name;

    private String password;

}
