package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private static long generatedId = 0L;

    private final Map<Long, Task> tasks;
    private final Map<Long, Subtask> subtasks;
    private final Map<Long, Epic> epics;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static long generateId() {
        return ++generatedId;
    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    public Map<Long, Epic> getEpics() {
        return epics;
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        removeAllSubtasks();
    }

    public Task getTaskById(long id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(long id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(long id) {
        return epics.get(id);
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task task, long id) {
        tasks.put(id, task);
    }

    public void updateSubtask(Subtask subtask, long id) {
        subtasks.put(id, subtask);
    }

    public void updateEpic(Epic epic, long id) {
        epics.put(id, epic);
    }

    public void removeTaskById(long id) {
        tasks.remove(id);
    }

    public void removeSubtaskById(long id) {
        subtasks.remove(id);
    }

    public void removeEpicById(long id) {
        subtasks.values().removeIf(subtask -> subtask.getEpicId() == id);
        epics.remove(id);
    }

    public void updateStatus(Task task, Status status) {
        if (task instanceof Epic) {
            updateEpicStatus(task.getId());
        } else if (task instanceof Subtask) {
            task.setStatus(status);
            updateEpicStatus(((Subtask) task).getEpicId());
        } else {
            task.setStatus(status);
        }
    }

    private void updateEpicStatus(long id) {
        Epic epic = epics.get(id);
        List<Subtask> subtasksByEpicId = getSubtasksByEpicId(id);

        boolean isAllNew = subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == id)
                .filter(subtask -> subtask.getStatus() == Status.NEW)
                .count() == subtasksByEpicId.size();

        boolean isAllDone = subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == id)
                .filter(subtask -> subtask.getStatus() == Status.DONE)
                .count() == subtasksByEpicId.size();

        if (subtasksByEpicId.isEmpty() || isAllNew) {
            epic.setStatus(Status.NEW);
        } else if (isAllDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROCESS);
        }
    }

    private List<Subtask> getSubtasksByEpicId(long epicId) {

        return subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .toList();
    }
}
