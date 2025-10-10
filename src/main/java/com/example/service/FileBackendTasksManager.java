package com.example.service;

import com.example.exception.ManagerLoadException;
import com.example.exception.ManagerSaveException;
import com.example.model.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackendTasksManager extends InMemoryTasksManager implements TaskManager {

    Path pathToFile;

    public FileBackendTasksManager(Path pathToFile) {
        super();
        this.pathToFile = pathToFile;
    }

    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "tasks.csv")
                .normalize().toAbsolutePath();
        System.out.println(filePath);
        FileBackendTasksManager.loadFromFile(filePath);
    }

    private void save(Task task) {

        try {
            if (!Files.exists(pathToFile)) {
                Files.createFile(pathToFile);
            }
            Files.writeString(pathToFile, "\n" + toString(task), StandardOpenOption.APPEND);
        } catch (FileAlreadyExistsException e) {
            System.out.println("File " + e.getFile() + " already exists");
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static void loadFromFile(Path filepath) throws IOException {
        if (Files.exists(filepath)) {
            String content = Files.readString(filepath);
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.trim().startsWith("id")) continue;
                Task task = fromString(line.trim());
            }
            System.out.println(content);
        } else {
            throw new ManagerLoadException("File does not exist");
        }
    }

    @Override
    public void addTask(Task task) throws IOException {
        tasks.put(task.getId(), task);
        save(task);
    }

    @Override
    public void addSubtask(Subtask subtask) throws IOException {
        subtasks.put(subtask.getId(), subtask);
        save(subtask);
    }

    @Override
    public void addEpic(Epic epic) throws IOException {
        epics.put(epic.getId(), epic);
        save(epic);
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

    public static Task fromString(String stringTask) {
        Task task;
        String[] parts = stringTask.split(",");
        long id = Long.parseLong(parts[0]);
        TaskType type = TaskType.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        long epicId = parts.length < 6 ? -1 : Long.parseLong(parts[5]);

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
