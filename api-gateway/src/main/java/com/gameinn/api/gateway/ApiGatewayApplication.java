package com.gameinn.api.gateway;

import com.gameinn.api.gateway.exception.AuthenticationFilterExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public ErrorWebExceptionHandler authenticationFilterExceptionHandler(){
		return new AuthenticationFilterExceptionHandler();
	}

}
