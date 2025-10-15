package com.example.service;

import com.example.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedList<Long> history;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }

    @Override
    public void add(Task task) {

        if (history.contains(task.getId())) {
            long id = task.getId();
            remove(id);
        }

        history.addFirst(task.getId());

        if (history.size() > 10) {
            history.removeLast();
        }
    }

    @Override
    public void remove(long id) {
        history.remove(id);
    }

    @Override
    public List<Long> getHistory() {
        return history;
    }
}
