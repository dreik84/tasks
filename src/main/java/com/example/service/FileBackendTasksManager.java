package com.example.service;

import com.example.model.Epic;
import com.example.model.Subtask;
import com.example.model.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
}
