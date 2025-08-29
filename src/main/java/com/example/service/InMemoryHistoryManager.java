package com.example.service;

import com.example.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Task> history;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
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

    private class Node {
        long id;
        Node prev;
        Node next;

        public Node(long id, Node prev, Node next) {
            this.id = id;
            this.prev = prev;
            this.next = next;
        }

        void linkLast(Task task) {
            history.put(id, task);
        }

        List<Task> getTasks() {
            return new ArrayList<>(history.values());
        }
    }
}
