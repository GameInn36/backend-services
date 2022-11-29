package com.gameinn.api.gateway.security;

import com.gameinn.api.gateway.models.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        return (((exchange, chain) -> {
            if(routerValidator.isSecured.test(exchange.getRequest())){
                log.error("isSecured passed");
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    log.error("55555555555555555");
                    throw new RuntimeException("Missing Authorization Header");
                }
                log.error("11111111111");
                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                try{
                    jwtTokenUtil.validateToken(authHeader);
                    log.error("2222222222222222");
                }catch (Exception e){
                    log.error("Error Validating Authentication Header", e);
                    List<String> details = new ArrayList<>();
                    details.add(e.getLocalizedMessage());
                    ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.UNAUTHORIZED.value(),"UNAUTHORIZED",details,exchange.getRequest().getPath().value());
                    ServerHttpResponse response = exchange.getResponse();

                    byte[] bytes = SerializationUtils.serialize(error);

                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                    response.writeWith(Flux.just(buffer));
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    log.error("33333333333333");
                    return response.setComplete();
                }
            }
            log.error("444444444444");
            return chain.filter(exchange);
        }));
    }

    public static class Config{

    }
}
