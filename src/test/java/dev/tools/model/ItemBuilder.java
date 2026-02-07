package dev.tools.model;

public class ItemBuilder {

    private Long id = 1L;
    private String name = "Default";
    private String description = "";
    private boolean completed = false;

    private ItemBuilder() {
    }

    public static ItemBuilder anItem() {
        return new ItemBuilder();
    }

    public ItemBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ItemBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }

    public Item build() {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setCompleted(completed);
        return item;
    }
}
