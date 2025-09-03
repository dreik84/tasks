package com.example.service;

import com.example.model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Node> history;
    private Node first;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
        first = null;
    }

    @Override
    public void add(Task task) {
        Node node = new Node(task);

        if (history.containsKey(task.getId())) {
            long id = task.getId();
            node.removeNode(history.get(id));
            remove(id);
        }
        node.linkLast(task);
        history.put(task.getId(), node);

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
        return history.values().stream().map(node -> node.task).collect(Collectors.toList());
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
            if (first != null) {
                this.after = first;
                first.before = this;
            }
            first = this;
        }

        List<Task> getTasks() {
            return history.values().stream().map(node -> node.task).collect(Collectors.toList());
        }

        void removeNode(Node node) {
            node.before.after = node.after;
            node.after.before = node.before;
            node.after = null;
            node.before = null;
        }
    }
}
