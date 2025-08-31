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
        Node node = new Node(task);
        node.linkLast(task);
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
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
            this.prev = null;
            this.next = null;
        }

        void linkLast(Task task) {
            if (history.containsKey(task.getId())) {
                remove(task.getId());
            }
            history.put(task.getId(), task);

            if (history.size() > 10) {
                history.remove(0);
            }
        }

        List<Task> getTasks() {
            return new ArrayList<>(history.values());
        }
    }
}
