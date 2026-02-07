package dev.tools.controller;

import dev.tools.service.ValidatorService;
import java.util.List;  // Unused - Checkstyle
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para validaciones. Demo para linter/SonarQube.
 */
@RestController
@RequestMapping("/api/validate")
public class ValidatorController {

    private final ValidatorService validatorService;

    public ValidatorController(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @GetMapping("/email")
    public ResponseEntity<Map<String, Object>> validateEmail(@RequestParam String value) {
        boolean valid = validatorService.isValidEmail(value);
        return ResponseEntity.ok(Map.of("valid", valid, "value", value));
    }

    @GetMapping("/name")
    public ResponseEntity<Map<String, Object>> validateName(@RequestParam String value) {
        boolean valid = validatorService.isValidName(value);
        return ResponseEntity.ok(Map.of("valid", valid));
    }
}
