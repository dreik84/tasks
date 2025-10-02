package com.example.service;

import com.example.model.Epic;
import com.example.model.Subtask;
import com.example.model.Task;
import com.example.model.TaskType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringJoiner;

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
        String epic = "";

        if (task instanceof Subtask) {
            taskType = TaskType.SUBTASK;
            epic = String.valueOf(((Subtask) task).getEpicId());
        } else if (task instanceof Epic) {
            taskType = TaskType.EPIC;
        } else {
            taskType = TaskType.TASK;
        }

        return task.getId() + "," + taskType + "," + task.getName() + ","
                + task.getStatus() + "," + task.getDescription() + "," + epic;
    }
}
