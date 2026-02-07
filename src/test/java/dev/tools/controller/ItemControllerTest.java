package dev.tools.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.tools.dto.CreateItemRequest;
import dev.tools.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static dev.tools.model.ItemBuilder.anItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@DisplayName("ItemController")
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Nested
    @DisplayName("GET /api/items")
    class ListItems {

        @Test
        void returns200WithItems() throws Exception {
            var items = List.of(anItem().withId(1L).withName("Item 1").build());
            when(itemService.findAll()).thenReturn(items);

            mockMvc.perform(get("/api/items"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Item 1"));
        }
    }

    @Nested
    @DisplayName("GET /api/items/{id}")
    class GetById {

        @Test
        void returns200WhenFound() throws Exception {
            var item = anItem().withId(1L).withName("Test").build();
            when(itemService.findById(1L)).thenReturn(java.util.Optional.of(item));

            mockMvc.perform(get("/api/items/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Test"));
        }

        @Test
        void returns404WhenNotFound() throws Exception {
            when(itemService.findById(999L)).thenReturn(java.util.Optional.empty());

            mockMvc.perform(get("/api/items/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("POST /api/items")
    class Create {

        @Test
        void returns201WithCreatedItem() throws Exception {
            var request = new CreateItemRequest("New item", "Description");
            var created = anItem().withId(1L).withName("New item").withDescription("Description").build();
            when(itemService.create(any(CreateItemRequest.class))).thenReturn(created);

            mockMvc.perform(post("/api/items")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value("New item"));

            verify(itemService).create(any(CreateItemRequest.class));
        }

        @Test
        void returns400WhenNameEmpty() throws Exception {
            var request = new CreateItemRequest("", "Description");
            when(itemService.create(any(CreateItemRequest.class)))
                    .thenThrow(new IllegalArgumentException("Name cannot be empty"));

            mockMvc.perform(post("/api/items")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error").value("Name cannot be empty"));
        }
    }

    @Nested
    @DisplayName("PATCH /api/items/{id}/toggle")
    class ToggleComplete {

        @Test
        void returns200WithToggledItem() throws Exception {
            var item = anItem().withId(1L).withCompleted(true).build();
            when(itemService.toggleComplete(1L)).thenReturn(java.util.Optional.of(item));

            mockMvc.perform(patch("/api/items/1/toggle"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.completed").value(true));
        }

        @Test
        void returns404WhenNotFound() throws Exception {
            when(itemService.toggleComplete(999L)).thenReturn(java.util.Optional.empty());

            mockMvc.perform(patch("/api/items/999/toggle"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("DELETE /api/items/{id}")
    class Delete {

        @Test
        void returns204WhenDeleted() throws Exception {
            when(itemService.deleteById(1L)).thenReturn(true);

            mockMvc.perform(delete("/api/items/1"))
                    .andExpect(status().isNoContent());

            verify(itemService).deleteById(1L);
        }

        @Test
        void returns404WhenNotFound() throws Exception {
            when(itemService.deleteById(999L)).thenReturn(false);

            mockMvc.perform(delete("/api/items/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET /api/items/stats")
    class Stats {

        @Test
        void returnsStats() throws Exception {
            when(itemService.count()).thenReturn(5L);
            when(itemService.countCompleted()).thenReturn(2L);

            mockMvc.perform(get("/api/items/stats"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.total").value(5))
                    .andExpect(jsonPath("$.completed").value(2));
        }
    }
}
