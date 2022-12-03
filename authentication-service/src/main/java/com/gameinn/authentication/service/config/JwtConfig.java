package com.gameinn.authentication.service.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
@Getter
@Configuration
public class JwtConfig {
    private final String secret = "mySecret";
    private final long validity = 20;
}
