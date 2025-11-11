package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTasksManager implements TaskManager {

    protected static long id = 0L;

    protected final Map<Long, Task> tasks;
    protected final Map<Long, Subtask> subtasks;
    protected final Map<Long, Epic> epics;
    protected final HistoryManager historyManager;

    public InMemoryTasksManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    public static long generateId() {
        return ++id;
    }

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        InMemoryTasksManager.id = id;
    }

    @Override
    public List<Long> getHistory() {
        return historyManager.getHistory();
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

        if (task == null) {
            throw new IllegalArgumentException("Task with id " + id + " not found");
        }

        historyManager.add(task.getId());
        return task;
    }

    @Override
    public Subtask getSubtaskById(long id) {
        Subtask subtask = subtasks.get(id);

        if (subtask == null) {
            throw new IllegalArgumentException("Subtask with id " + id + " not found");
        }

        historyManager.add(subtask.getId());
        return subtask;
    }

    @Override
    public Epic getEpicById(long id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            throw new IllegalArgumentException("Epic with id " + id + " not found");
        }

        historyManager.add(epic.getId());
        return epic;
    }

    @Override
    public void addTask(Task task) throws IOException {
        if (task instanceof Epic epic) {
            epics.put(epic.getId(), epic);
        } else if (task instanceof Subtask subtask) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtask(subtask.getId());
        } else {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateTask(Task task, long id) {
        if (task instanceof Epic epic) {
            if (epics.get(id) == null) {
                throw new IllegalArgumentException("Epic with id " + id + " not found");
            }
            epics.put(id, epic);
        } else if (task instanceof Subtask subtask) {
            if (subtasks.get(id) == null) {
                throw new IllegalArgumentException("Subtask with id " + id + " not found");
            }
            subtasks.put(id, subtask);
        } else {
            if (tasks.get(id) == null) {
                throw new IllegalArgumentException("Task with id " + id + " not found");
            }
            tasks.put(id, task);
        }
    }

    @Override
    public void removeTaskById(long id) {
        if (tasks.get(id) == null) {
            throw new IllegalArgumentException("Task with id " + id + " not found");
        }
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(long id) {
        if (subtasks.get(id) == null) {
            throw new IllegalArgumentException("Subtask with id " + id + " not found");
        }
        Subtask subtask = subtasks.get(id);
        Epic epic = getEpicById(subtask.getEpicId());
        epic.removeSubtask(subtask.getId());
        subtasks.remove(id);
    }

    @Override
    public void removeEpicById(long id) {
        if (epics.get(id) == null) {
            throw new IllegalArgumentException("Epic with id " + id + " not found");
        }
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
