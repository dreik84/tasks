package com.example.service;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedList<Long> history;

    public InMemoryHistoryManager() {
        history = new LinkedList<>();
    }

    @Override
    public void add(long id) {

        if (history.contains(id)) {
            remove(id);
        }

        history.addLast(id);

        if (history.size() > 10) {
            history.removeFirst();
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
