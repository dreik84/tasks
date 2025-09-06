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
        last = null;
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
            history.remove(last.task.getId());
            node.removeNode(last);
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
        private Task task;
        private Node before;
        private Node after;

        void linkFirst(Task task) {
            this.task = task;

            if (first == null) {
                last = this;
            } else {
                this.after = first;
                first.before = this;
            }
            first = this;
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
