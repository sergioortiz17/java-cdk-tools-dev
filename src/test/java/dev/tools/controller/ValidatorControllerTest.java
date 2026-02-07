package dev.tools.controller;

import dev.tools.service.ValidatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ValidatorController.class)
@DisplayName("ValidatorController")
class ValidatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidatorService validatorService;

    @Test
    void validateEmailReturns200WithValidResult() throws Exception {
        when(validatorService.isValidEmail("test@example.com")).thenReturn(true);

        mockMvc.perform(get("/api/validate/email").param("value", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.value").value("test@example.com"));
    }

    @Test
    void validateEmailReturns200WithInvalidResult() throws Exception {
        when(validatorService.isValidEmail("invalid")).thenReturn(false);

        mockMvc.perform(get("/api/validate/email").param("value", "invalid"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(false));
    }

    @Test
    void validateNameReturns200WithValidResult() throws Exception {
        when(validatorService.isValidName("John")).thenReturn(true);

        mockMvc.perform(get("/api/validate/name").param("value", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true));
    }
}
