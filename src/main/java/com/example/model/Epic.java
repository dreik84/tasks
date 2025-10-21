package com.example.model;

import java.util.HashSet;
import java.util.Set;

public class Epic extends Task {

    private Set<Long> subtasks;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtasks = new HashSet<>();
    }

    public Epic(Long id, String name, String description, Status status) {
        super(id, name, description, status);
        subtasks = new HashSet<>();
    }

    @Override
    public String toString() {
        return "Epic{" + "name='" + getName() + '\''
                + ", description='" + getDescription() + '\''
                + ", id=" + getId()
                + ", status=" + getStatus() + '}';
    }

    public Set<Long> getSubtasks() {
        return subtasks;
    }

    public void addSubtask(Long id) {
        subtasks.add(id);
    }

    public void removeSubtask(Long id) {
        subtasks.remove(id);
    }
}
