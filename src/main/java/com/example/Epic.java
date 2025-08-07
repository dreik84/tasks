package com.example;

import java.util.HashMap;
import java.util.Map;

public class Epic extends Task {

    private final Map<Long, Subtask> subtasks;

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

    public void updateStatus() {

        boolean isAllNew = subtasks.values().stream()
                .filter(subtask -> subtask.status == Status.NEW)
                .count() == subtasks.size();

        boolean isAllDone = subtasks.values().stream()
                .filter(subtask -> subtask.status == Status.DONE)
                .count() == subtasks.size();

        if (subtasks.isEmpty() || isAllNew) {
            status = Status.NEW;
        } else if (isAllDone) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROCESS;
        }

    }
}
