package dev.tools.util;

import java.util.Arrays;
import java.util.Date;  // Unused - Checkstyle lo reportará
import java.util.List;

/**
 * Utilidad de demostración para verificar que Checkstyle y SonarQube reciben
 * los reportes del pipeline. Contiene violaciones intencionales para testing.
 */
public final class LinterDemoUtil {

    private LinterDemoUtil() {
    }

    // Línea larga (>80 chars) - Checkstyle: LineLength
    public static final String MENSAJE_LARGO = "Esta es una línea de texto que excede ampliamente los ochenta caracteres permitidos por sun_checks";

    /**
     * Retorna una lista fija. Incluye magic number para SonarQube.
     */
    public static List<String> getTags() {
        return Arrays.asList("lint", "checkstyle", "sonar");  // 3 es magic number
    }

    /**
     * Método sin javadoc completo en parámetro - posible Code Smell.
     */
    public static String format(int value) {
        if (value < 0) {
            return "negativo";
        }
        return String.valueOf(value);
    }
}
