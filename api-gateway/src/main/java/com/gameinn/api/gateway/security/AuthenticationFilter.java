package com.gameinn.api.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Objects;

@Component
@RefreshScope
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouterValidator routerValidator;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationFilter(RouterValidator routerValidator, JwtTokenUtil jwtTokenUtil){
        super(Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
        this.routerValidator = routerValidator;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(routerValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorization Header");
                }
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                try{
                    jwtTokenUtil.validateToken(authHeader);
                }catch (AuthenticationException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config{

    }
}
