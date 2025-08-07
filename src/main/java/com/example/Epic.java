package com.example;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {

    private Map<Long, Subtask> subtasks;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtasks = new HashMap<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.id, subtask);
    }

    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }
}
