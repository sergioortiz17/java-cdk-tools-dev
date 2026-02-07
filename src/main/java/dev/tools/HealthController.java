package dev.tools;

import dev.tools.util.LinterDemoUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class HealthController {

    private static String readVersion() {
        String version = System.getenv("APP_VERSION");
        if (version != null && !version.isEmpty()) {
            return version;
        }
        try {
            Path versionFile = Path.of("/app/version.txt");
            if (Files.exists(versionFile)) {
                return Files.readString(versionFile).trim();
            }
        } catch (IOException ignored) {
            // fall through to unknown
        }
        return "unknown";
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        String version = readVersion();

        Map<String, String> body = new TreeMap<>(Map.of(
                "status", "ok",
                "app", "java-cdk-tools-dev",
                "message", "Hello from ECS!",
                "tags", String.join(",", LinterDemoUtil.getTags())
        ));
        body.put("commit", version);

        return ResponseEntity.ok(body);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
