package com.gameinn.game.service.feignClient;

import com.gameinn.game.service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", url = "${feign.userservice.url}")
public interface UserService {
    @GetMapping("/user/{userId}")
    User getUserById(@PathVariable String userId);

    @GetMapping("/user/")
    List<User> getAllUsers();
}
