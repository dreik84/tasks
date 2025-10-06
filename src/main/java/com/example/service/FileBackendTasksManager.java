package com.example.service;

import com.example.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackendTasksManager extends InMemoryTasksManager implements TaskManager {

    Path pathToFile;

    public FileBackendTasksManager(Path pathToFile) {
        super();
        this.pathToFile = pathToFile;
    }

    private void save() throws IOException {
        if (!Files.exists(pathToFile)) {
            Files.createFile(pathToFile);
        }

        Files.readString(pathToFile);
    }

    @Override
    public void addTask(Task task) throws IOException {
        tasks.put(task.getId(), task);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) throws IOException {
        subtasks.put(subtask.getId(), subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) throws IOException {
        epics.put(epic.getId(), epic);
        save();
    }

    public String toString(Task task) {
        TaskType taskType;
        String epicId = "";

        if (task instanceof Subtask) {
            taskType = TaskType.SUBTASK;
            epicId = String.valueOf(((Subtask) task).getEpicId());
        } else if (task instanceof Epic) {
            taskType = TaskType.EPIC;
        } else {
            taskType = TaskType.TASK;
        }

        return task.getId() + "," + taskType + "," + task.getName() + ","
                + task.getStatus() + "," + task.getDescription() + "," + epicId;
    }

    public Task fromString(String stringTask) {
        Task task;
        String[] parts = stringTask.split(",");
        long id = Long.parseLong(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        long epicId = parts[5].trim().isEmpty() ? -1 : Long.parseLong(parts[5]);

        if (type == TaskType.SUBTASK) {
            task = new Subtask(id, name, description, status, epicId);
        } else if (type == TaskType.EPIC) {
            task = new Epic(id, name, description, status);
        } else {
            task = new Task(id, name, description, status);
        }

        return task;
    }

    public static String toString(HistoryManager manager) {
        return manager.toString();
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();

        return list;
    }
}
