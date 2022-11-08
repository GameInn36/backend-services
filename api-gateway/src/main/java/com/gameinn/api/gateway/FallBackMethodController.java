package com.gameinn.api.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod(){
        return "User Service is taking longer than expected. " +
                " Please try again later";
    }

    @GetMapping("/gameServiceFallBack")
    public String gameServiceFallBackMethod(){
        return "Game Service is taking longer than expected. " +
                " Please try again later";
    }

    @GetMapping("/reviewServiceFallBack")
    public String reviewServiceFallBackMethod(){
        return "Review Service is taking longer than expected. " +
                " Please try again later";
    }
}
