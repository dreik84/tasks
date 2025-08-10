package com.example.model;

import com.example.service.TaskManager;

public class Task {

    protected String name;
    protected String description;
    protected Long id;
    protected Status status;

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = TaskManager.generateId();
    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" + "name='" + name + '\''
                + ", description='" + description + '\''
                + ", id=" + id
                + ", status=" + status + '}';
    }
}
