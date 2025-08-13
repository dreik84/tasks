package com.example.model;

public class Epic extends Task {

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    @Override
    public String toString() {
        return "Epic{" + "name='" + getName() + '\''
                + ", description='" + getDescription() + '\''
                + ", id=" + getId()
                + ", status=" + getStatus() + '}';
    }
}
