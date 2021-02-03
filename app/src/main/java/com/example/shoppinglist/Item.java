package com.example.shoppinglist;

public class Item {
    private final String id;
    private final String itemName;
    private final long creationTime;

    public Item(String id, String itemName, long creationTime) {
        this.id = id;
        this.itemName = itemName;
        this.creationTime = creationTime;
    }

    public String getItemName() {
        return itemName;
    }

    public String getId() {
        return id;
    }

    public long getCreationTime() {
        return creationTime;
    }
}
