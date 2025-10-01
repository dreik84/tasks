package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    public List<Task> getHistory();

    public Map<Long, Task> getTasks();

    public Map<Long, Subtask> getSubtasks();

    public Map<Long, Epic> getEpics();

    public void removeAllTasks();

    public void removeAllSubtasks();

    public void removeAllEpics();

    public Task getTaskById(long id);

    public Subtask getSubtaskById(long id);

    public Epic getEpicById(long id);

    public void addTask(Task task) throws IOException;

    public void addSubtask(Subtask subtask) throws IOException;

    public void addEpic(Epic epic) throws IOException;

    public void updateTask(Task task, long id);

    public void updateSubtask(Subtask subtask, long id);

    public void updateEpic(Epic epic, long id);

    public void removeTaskById(long id);

    public void removeSubtaskById(long id);

    public void removeEpicById(long id);

    public void updateStatus(Task task, Status status);
}
