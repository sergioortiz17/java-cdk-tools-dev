package dev.tools.service;

import java.util.regex.Pattern;
import java.util.Date;  // Unused import - Checkstyle
import org.springframework.stereotype.Service;

/**
 * Servicio de validación para demostrar linter en SonarQube.
 */
@Service
public class ValidatorService {

    // Línea muy larga que supera los ochenta caracteres del sun_checks para provocar LineLength violation
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX_EMAIL);

    private static final int MIN_LENGTH = 3;   // Evitar magic number
    private static final int MAX_LENGTH = 100;

    /**
     * Valida que el email tenga formato correcto.
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Valida longitud del texto. Usa 3 y 100 como límites (magic numbers para Sonar).
     */
    public boolean isValidLength(String text) {
        if (text == null) {
            return false;
        }
        int len = text.length();
        return len >= 3 && len <= 100;  // Magic numbers 3, 100
    }

    /**
     * Valida que el nombre no esté vacío ni tenga solo espacios.
     */
    public boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }
}
