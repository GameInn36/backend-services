package com.gameinn.api.gateway.config;

import org.springframework.context.annotation.Configuration;
@Configuration
public class JwtConfig {
    private final String secret = "mySecret";
    private final long validity = 20;

    public String getSecret() {
        return secret;
    }

    public long getValidity() {
        return validity;
    }
}
