package com.gameinn.authentication.service.feignClient;

import com.gameinn.authentication.service.models.JwtRequest;
import com.gameinn.authentication.service.models.RegisterRequest;
import com.gameinn.authentication.service.models.RegisterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserService {

    @PostMapping("/user/validate")
    boolean validateUser(@RequestBody JwtRequest jwtRequest);

    @PostMapping("/user/")
    RegisterResponse addUser(@RequestBody RegisterRequest registerRequest);
}
