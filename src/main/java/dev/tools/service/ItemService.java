package dev.tools.service;

import dev.tools.dto.CreateItemRequest;
import dev.tools.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemService {

    private final List<Item> items = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ItemService() {
        items.add(new Item(idGenerator.getAndIncrement(), "Demo item", "Created for demo", false));
    }

    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    public Optional<Item> findById(Long id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    public Item create(CreateItemRequest request) {
        String name = request.getName() != null ? request.getName().trim() : "";
        String description = request.getDescription() != null ? request.getDescription().trim() : "";

        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        Item item = new Item(idGenerator.getAndIncrement(), name, description, false);
        items.add(item);
        return item;
    }

    public Optional<Item> toggleComplete(Long id) {
        return findById(id)
                .map(item -> {
                    item.setCompleted(!item.isCompleted());
                    return item;
                });
    }

    public boolean deleteById(Long id) {
        return items.removeIf(item -> item.getId().equals(id));
    }

    public long count() {
        return items.size();
    }

    public long countCompleted() {
        return items.stream()
                .filter(Item::isCompleted)
                .count();
    }
}
