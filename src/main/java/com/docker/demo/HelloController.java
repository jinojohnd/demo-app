package com.docker.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
class HelloController {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger log
            = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/")
    public String hello() {
        return "Hello, Spring Boot in Docker V6 for demo!";
    }

    @GetMapping("/internal-info")
    public String internalInfo() {
        String url = "http://internal-service:8080/info";
        log.info("Retrieving internal info from {}", url);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping("/stress-test")
    public String stressTest(
            @RequestParam(defaultValue = "10000") int size,
            @RequestParam(defaultValue = "1000") int iterations) {

        log.info("Starting stress test with size={} and iterations={}", size, iterations);

        long start = System.currentTimeMillis();

        // Allocate a large array to consume memory
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = Math.random();
        }

        log.debug("Memory allocation complete. Starting CPU-intensive calculations...");

        // Do some CPU-intensive calculations
        double sum = 0;
        for (int i = 0; i < iterations; i++) {
            for (double v : array) {
                sum += Math.sqrt(v) * Math.sin(v);
            }
            if (i % (iterations / 10 == 0 ? 1 : iterations / 10) == 0) {
                log.debug("Progress: {}/{} iterations complete", i, iterations);
            }
        }

        long elapsed = System.currentTimeMillis() - start;

        log.info("Stress test completed in {} ms. Final result={}", elapsed, sum);

        return "Stress test completed in " + elapsed + " ms. Result = " + sum;
    }
}
