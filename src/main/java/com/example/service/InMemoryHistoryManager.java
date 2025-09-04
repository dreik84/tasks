package com.example.service;

import com.example.model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Node> history;
    private Node first;
    private Node last;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
        first = null;
    }

    @Override
    public void add(Task task) {
        Node node = new Node();

        if (history.containsKey(task.getId())) {
            long id = task.getId();
            node.removeNode(history.get(id));
            remove(id);
        }
        node.linkFirst(task);
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

        void linkFirst(Task task) {
            if (first == null) {
                first = this;
                last = this;
            } else {
                this.after = first;
                first.before = this;
            }
        }

        List<Task> getTasks() {
            return history.values().stream().map(node -> node.task).collect(Collectors.toList());
        }

        void removeNode(Node node) {

            if (node == first) {
                first = node.after;
            } else if (node == last) {
                last = node.before;
            } else {
                node.before.after = node.after;
                node.after.before = node.before;
            }

            node.after = null;
            node.before = null;
        }
    }
}
