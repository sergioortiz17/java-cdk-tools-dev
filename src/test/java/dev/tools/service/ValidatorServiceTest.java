package dev.tools.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ValidatorService")
class ValidatorServiceTest {

    private ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        validatorService = new ValidatorService();
    }

    @Nested
    @DisplayName("isValidEmail")
    class IsValidEmail {

        @Test
        void returnsTrueForValidEmail() {
            assertThat(validatorService.isValidEmail("user@example.com")).isTrue();
            assertThat(validatorService.isValidEmail("test.user+tag@domain.co")).isTrue();
        }

        @Test
        void returnsFalseForNull() {
            assertThat(validatorService.isValidEmail(null)).isFalse();
        }

        @Test
        void returnsFalseForEmpty() {
            assertThat(validatorService.isValidEmail("")).isFalse();
            assertThat(validatorService.isValidEmail("   ")).isFalse();
        }

        @Test
        void returnsFalseForInvalidFormat() {
            assertThat(validatorService.isValidEmail("invalid")).isFalse();
            assertThat(validatorService.isValidEmail("@domain.com")).isFalse();
            assertThat(validatorService.isValidEmail("user@")).isFalse();
        }
    }

    @Nested
    @DisplayName("isValidLength")
    class IsValidLength {

        @Test
        void returnsTrueWhenLengthBetween3And100() {
            assertThat(validatorService.isValidLength("abc")).isTrue();
            assertThat(validatorService.isValidLength("a".repeat(100))).isTrue();
        }

        @Test
        void returnsFalseWhenTooShort() {
            assertThat(validatorService.isValidLength("ab")).isFalse();
            assertThat(validatorService.isValidLength("")).isFalse();
        }

        @Test
        void returnsFalseWhenTooLong() {
            assertThat(validatorService.isValidLength("a".repeat(101))).isFalse();
        }

        @Test
        void returnsFalseForNull() {
            assertThat(validatorService.isValidLength(null)).isFalse();
        }
    }

    @Nested
    @DisplayName("isValidName")
    class IsValidName {

        @Test
        void returnsTrueForNonBlankName() {
            assertThat(validatorService.isValidName("John")).isTrue();
            assertThat(validatorService.isValidName("  John  ")).isTrue();
        }

        @Test
        void returnsFalseForNull() {
            assertThat(validatorService.isValidName(null)).isFalse();
        }

        @Test
        void returnsFalseForBlank() {
            assertThat(validatorService.isValidName("")).isFalse();
            assertThat(validatorService.isValidName("   ")).isFalse();
        }
    }
}
