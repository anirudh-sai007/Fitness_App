package com.example.tbar;

public class User {
    private String name;
    private String dateAdded;

    public User(String name, String dateAdded) {
        this.name = name;
        this.dateAdded = dateAdded;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getDateAdded() {
        return dateAdded;
    }
}

