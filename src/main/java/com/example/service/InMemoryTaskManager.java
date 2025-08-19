package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private static long generatedId = 0L;

    private final Map<Long, Task> tasks;
    private final Map<Long, Subtask> subtasks;
    private final Map<Long, Epic> epics;
    private final List<Task> history;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        history = new LinkedList<>();
    }

    public static long generateId() {
        return ++generatedId;
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public Map<Long, Task> getTasks() {
        return tasks;
    }

    @Override
    public Map<Long, Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public Map<Long, Epic> getEpics() {
        return epics;
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        removeAllSubtasks();
    }

    @Override
    public Task getTaskById(long id) {
        Task task = tasks.get(id);
        history.add(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(long id) {
        Subtask subtask = subtasks.get(id);
        history.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(long id) {
        Epic epic = epics.get(id);
        history.add(epic);
        return epic;
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(Task task, long id) {
        tasks.put(id, task);
    }

    @Override
    public void updateSubtask(Subtask subtask, long id) {
        subtasks.put(id, subtask);
    }

    @Override
    public void updateEpic(Epic epic, long id) {
        epics.put(id, epic);
    }

    @Override
    public void removeTaskById(long id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(long id) {
        subtasks.remove(id);
    }

    @Override
    public void removeEpicById(long id) {
        subtasks.values().removeIf(subtask -> subtask.getEpicId() == id);
        epics.remove(id);
    }

    @Override
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
