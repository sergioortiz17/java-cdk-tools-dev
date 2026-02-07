package dev.tools.service;

import dev.tools.dto.CreateItemRequest;
import dev.tools.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ItemService")
class ItemServiceTest {

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService();
    }

    @Nested
    @DisplayName("findAll")
    class FindAll {

        @Test
        void returnsInitialDemoItem() {
            var items = itemService.findAll();

            assertThat(items).hasSize(1);
            assertThat(items.get(0).getName()).isEqualTo("Demo item");
        }

        @Test
        void returnsCopyOfItems() {
            var items1 = itemService.findAll();
            var items2 = itemService.findAll();

            assertThat(items1).isNotSameAs(items2);
        }
    }

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        void createsItemWithGeneratedId() {
            var request = new CreateItemRequest("Test item", "Test description");
            Item item = itemService.create(request);

            assertThat(item.getId()).isNotNull();
            assertThat(item.getName()).isEqualTo("Test item");
            assertThat(item.getDescription()).isEqualTo("Test description");
            assertThat(item.isCompleted()).isFalse();
        }

        @Test
        void addsItemToList() {
            var request = new CreateItemRequest("New item", "");
            itemService.create(request);

            assertThat(itemService.findAll()).hasSize(2);
        }

        @Test
        void trimsWhitespace() {
            var request = new CreateItemRequest("  trimmed  ", "  desc  ");
            Item item = itemService.create(request);

            assertThat(item.getName()).isEqualTo("trimmed");
            assertThat(item.getDescription()).isEqualTo("desc");
        }

        @Test
        void throwsWhenNameEmpty() {
            var request = new CreateItemRequest("", "description");

            assertThatThrownBy(() -> itemService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Name cannot be empty");
        }

        @Test
        void throwsWhenNameBlank() {
            var request = new CreateItemRequest("   ", "description");

            assertThatThrownBy(() -> itemService.create(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Name cannot be empty");
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        void returnsItemWhenExists() {
            var items = itemService.findAll();
            Long id = items.get(0).getId();

            var found = itemService.findById(id);

            assertThat(found).isPresent();
            assertThat(found.get().getId()).isEqualTo(id);
        }

        @Test
        void returnsEmptyWhenNotExists() {
            var found = itemService.findById(999L);

            assertThat(found).isEmpty();
        }
    }

    @Nested
    @DisplayName("toggleComplete")
    class ToggleComplete {

        @Test
        void togglesFromFalseToTrue() {
            var item = itemService.findAll().get(0);
            assertThat(item.isCompleted()).isFalse();

            var result = itemService.toggleComplete(item.getId());

            assertThat(result).isPresent();
            assertThat(result.get().isCompleted()).isTrue();
        }

        @Test
        void togglesFromTrueToFalse() {
            var item = itemService.findAll().get(0);
            itemService.toggleComplete(item.getId());

            var result = itemService.toggleComplete(item.getId());

            assertThat(result).isPresent();
            assertThat(result.get().isCompleted()).isFalse();
        }

        @Test
        void returnsEmptyWhenNotFound() {
            var result = itemService.toggleComplete(999L);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("deleteById")
    class DeleteById {

        @Test
        void removesItemAndReturnsTrue() {
            var item = itemService.findAll().get(0);
            Long id = item.getId();

            boolean deleted = itemService.deleteById(id);

            assertThat(deleted).isTrue();
            assertThat(itemService.findById(id)).isEmpty();
        }

        @Test
        void returnsFalseWhenNotFound() {
            boolean deleted = itemService.deleteById(999L);

            assertThat(deleted).isFalse();
        }
    }

    @Nested
    @DisplayName("stats")
    class Stats {

        @Test
        void countReturnsCorrectTotal() {
            itemService.create(new CreateItemRequest("A", ""));
            itemService.create(new CreateItemRequest("B", ""));

            assertThat(itemService.count()).isEqualTo(3);
        }

        @Test
        void countCompletedReturnsCorrectValue() {
            var item = itemService.findAll().get(0);
            itemService.toggleComplete(item.getId());

            assertThat(itemService.countCompleted()).isEqualTo(1);
        }
    }
}
