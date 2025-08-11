package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private static Long generatedId = 0L;

    private Map<Long, Task> tasks;
    private Map<Long, Subtask> subTasks;
    private Map<Long, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static Long generateId() {
        return ++generatedId;
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

    public void removeSubtasks() {
        subTasks.clear();

        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
        }
    }

    public void removeEpics() {
        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
        }

        epics.clear();
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
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask, Epic epic) {
        subTasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task task, Long id) {
        tasks.put(id, task);
    }

    public void updateSubtask(Subtask subtask, Long id) {
        subTasks.put(id, subtask);
        subtask.getEpic().getSubtasks().put(id, subtask);
    }

    public void updateEpic(Epic epic, Long id) {
        epics.put(id, epic);
    }

    public void removeTaskById(Long id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(Long id) {
        Subtask subtask = subTasks.get(id);
        subtask.getEpic().removeSubtaskById(id);
        subTasks.remove(id);
    }

    public void removeEpicById(Long id) {
        Epic epic = epics.get(id);
        epic.removeAllSubtasks();
        epics.remove(id);
    }

    public Map<Long, Subtask> getSubtasksByEpic(Epic epic) {
        return epic.getSubtasks();
    }

    public void updateStatus(Task task, Status status) {
        if (task instanceof Epic) {
            ((Epic) task).updateStatus();
        } else if (task instanceof Subtask) {
            task.setStatus(status);
            ((Subtask) task).getEpic().updateStatus();
        } else {
            task.setStatus(status);
        }
    }
}
