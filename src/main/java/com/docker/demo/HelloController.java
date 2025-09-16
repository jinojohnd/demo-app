package com.docker.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class HelloController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot in Docker V6 for demo!";
    }

    @GetMapping("/internal-info")
    public String internalInfo() {
        String url = "http://internal-service:8080/info";
        return restTemplate.getForObject(url, String.class);
    }
}
