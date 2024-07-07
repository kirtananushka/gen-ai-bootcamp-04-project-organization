package com.epam.training.gen.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "client-azureopenai")
public class OpenAIProperties {
    private String deploymentName;
    private String key;
    private String endpoint;
}
