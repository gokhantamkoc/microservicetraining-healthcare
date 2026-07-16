package com.gokhantamkoc.microservicetraining.healthcare.gateway;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FallbackController {
    @GetMapping("/fallback")
    Map<String, String> fallback() {
        return Map.of("status", "degraded", "message", "Downstream service is temporarily unavailable");
    }
}

