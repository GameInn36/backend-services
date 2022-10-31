package com.gameinn.review.service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @GetMapping
    public String getReviews()
    {
        return "Hello from Reviews!";
    }
}
