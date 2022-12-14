package com.gameinn.api.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gameinn.api.gateway.models.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class AuthenticationFilterExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.UNAUTHORIZED.value(),ex.getClass().getSimpleName(),ex.getMessage());
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(error);
        } catch (JsonProcessingException e) {
            bytes = e.getMessage().getBytes(StandardCharsets.UTF_8);
        }
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }
}
