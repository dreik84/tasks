package com.example;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private static Long id = 0L;

    private Map<Long, Task> tasks;
    private Map<Long, Subtask> subTasks;
    private Map<Long, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static Long generateId() {
        return ++id;
    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public Map<Long, Subtask> getSubTasks() {
        return subTasks;
    }

    public Map<Long, Epic> getEpics() {
        return epics;
    }

    public void removeTasks() {
        for (var key : tasks.keySet()) {
            tasks.remove(key);
        }
    }

    public void removeSubTasks() {
        subTasks = new HashMap<>();
    }

    public void removeEpics() {
        epics = new HashMap<>();
    }

    public Task getTaskById(Long id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(Long id) {
        return subTasks.get(id);
    }

    public Epic getEpicById(Long id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        tasks.put(task.id, task);
    }

    public void addSubtask(Subtask subtask, Epic epic) {
        subTasks.put(subtask.id, subtask);
        epics.put(subtask.id, epic);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.id, epic);
    }

    public void updateTask(Task task, Long id) {
        tasks.put(id, task);
    }

    public void updateSubtask(Subtask subtask, Long id) {
        subTasks.put(id, subtask);
    }

    public void updateEpic(Epic epic, Long id) {
        epics.put(id, epic);
    }

    public void removeTaskById(Long id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(Long id) {
        subTasks.remove(id);
    }

    public void removeEpicById(Long id) {
        epics.remove(id);
    }

    public Map<Long, Subtask> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public void setStatus(Status status) {

    }
}
