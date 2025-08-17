package com.example.model;

import com.example.service.TaskManager;

public class Task {

    private String name;
    private String description;
    private final Long id;
    private Status status;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = TaskManager.generateId();
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", id=" + id
                + ", status=" + status + '}';
    }
}
