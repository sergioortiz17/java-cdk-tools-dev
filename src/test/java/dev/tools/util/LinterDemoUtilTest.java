package dev.tools.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LinterDemoUtil")
class LinterDemoUtilTest {

    @Test
    void getTagsReturnsExpectedList() {
        List<String> tags = LinterDemoUtil.getTags();

        assertThat(tags).containsExactly("lint", "checkstyle", "sonar");
    }

    @Test
    void getDefaultLimitReturns42() {
        assertThat(LinterDemoUtil.getDefaultLimit()).isEqualTo(42);
    }

    @Test
    void formatReturnsNegativoForNegative() {
        assertThat(LinterDemoUtil.format(-5)).isEqualTo("negativo");
    }

    @Test
    void formatReturnsStringForPositive() {
        assertThat(LinterDemoUtil.format(10)).isEqualTo("10");
    }

    @Test
    void mensajeLargoIsNotEmpty() {
        assertThat(LinterDemoUtil.MENSAJE_LARGO).isNotEmpty();
    }
}
