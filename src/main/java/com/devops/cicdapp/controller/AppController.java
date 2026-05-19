package com.devops.cicdapp.controller;


import com.devops.cicdapp.model.HealthResponse;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppController {
    private final MeterRegistry meterRegistry;

    private final Counter helloCounter;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    public AppController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;

        this.helloCounter = Counter.builder("api.hello.requests")
                .description("Total calls to the hello endpoint")
                .tag("endpoint", "/api/hello")
                .register(meterRegistry);
    }


    @GetMapping("/hello")
    public Map<String, String> hello() {
        helloCounter.increment();
        return Map.of(
                "message", "Hello from CI/CD Pipeline Monitoring System!",
                "version", appVersion
        );

    }

    @GetMapping("/health")
    public HealthResponse health(){
        return new HealthResponse(
                "UP",
                "Application is running",
                appVersion
        );


    }

    @GetMapping("/info")
    public Map<String,String> info(){
        return Map.of(
                "app","cicd-monitoring-project",
                "version",appVersion,
                "java",System.getProperty("java.version"),
                "os",System.getProperty("os.name")
        );


    }

    @GetMapping("/load")
    public Map<String,String> simulateLoad(@RequestParam(defaultValue = "100") int iterations) {
        long result = 0;
        for(int i=0;i<iterations*1000;i++){
            result+=Math.sqrt(i);
        }
        return Map.of("status","done","result",String.valueOf(result));



    }

}

