package com.example.service;

import com.example.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Task> history;
    private Node first;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
        first = null;
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);
        node.linkLast(task);

        if (first != null) {
            node.after = first;
            first.before = node;
        }
        first = node;
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
        Node before;
        Node after;

        Node(Task task) {
            this.task = task;
            this.before = null;
            this.after = null;
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

        void removeNode(Node node) {
            node.before.after = node.after;
            node.after.before = node.before;
            node.after = null;
            node.before = null;
        }
    }
}
