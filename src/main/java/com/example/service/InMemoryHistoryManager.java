package com.example.service;

import com.example.model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedHashMap<Long, Task> history;

    public InMemoryHistoryManager() {
        history = new LinkedHashMap<>();
    }

    @Override
    public void add(Task task) {
        if (history.containsKey(task.getId())) {
            remove(task.getId());
        }
        history.put(task.getId(), task);

        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public void remove(long id) {
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history.values());
    }
}
