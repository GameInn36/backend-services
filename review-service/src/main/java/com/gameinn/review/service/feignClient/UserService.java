package com.gameinn.review.service.feignClient;

import com.gameinn.review.service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${feign.userservice.url}")
public interface UserService {
    @GetMapping("/user/{userId}")
    User getUserById(@PathVariable String userId);
}
