package dev.tools;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        String version = System.getenv("APP_VERSION");
        if (version == null || version.isEmpty()) {
            version = "unknown";
        }

        Map<String, String> body = new TreeMap<>(Map.of(
                "status", "ok",
                "app", "java-cdk-tools-dev",
                "message", "Hello from ECS!"
        ));
        body.put("commit", version);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
