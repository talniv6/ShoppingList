package com.example.shoppinglist;

public class Item {
    private final String id;
    private final String itemName;
    private final long creationTime;
    private final String user;

    public Item(String id, String itemName, long creationTime, String user) {
        this.id = id;
        this.itemName = itemName;
        this.creationTime = creationTime;
        this.user = user;
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

    public String getUser() {
        return user;
    }
}
