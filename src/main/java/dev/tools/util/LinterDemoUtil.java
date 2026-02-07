package dev.tools.util;

import java.util.Arrays;
import java.util.Date;  // Unused - Checkstyle
import java.util.List;
import java.util.HashSet;  // Unused - Checkstyle

/**
 * Utilidad de demostración para verificar que Checkstyle y SonarQube reciben
 * los reportes del pipeline. Contiene violaciones intencionales para testing.
 */
public final class LinterDemoUtil {

    private LinterDemoUtil() {
    }

    // Línea larga (>80 chars) - Checkstyle: LineLength
    public static final String MENSAJE_LARGO = "Esta es una línea de texto que excede ampliamente los ochenta caracteres permitidos por sun_checks";

    // Otra línea demasiado larga para provocar violación de LineLength en el reporte de Checkstyle
    public static final String OTRO_MENSAJE = "Segunda constante con texto que supera ampliamente el límite de caracteres por línea configurado";

    /**
     * Retorna una lista fija. Incluye magic number para SonarQube.
     */
    public static List<String> getTags() {
        return Arrays.asList("lint", "checkstyle", "sonar");  // magic number 3
    }

    /**
     * Método con magic number para Sonar/Checkstyle.
     */
    public static int getDefaultLimit() {
        return 42;  // Magic number
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
