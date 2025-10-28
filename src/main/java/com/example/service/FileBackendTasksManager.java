package com.example.service;

import com.example.exception.ManagerLoadException;
import com.example.exception.ManagerSaveException;
import com.example.model.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

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
        TaskManager taskManager = FileBackendTasksManager.loadFromFile(filePath);
        System.out.println(taskManager.getHistory());
        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());

    }

    private void save(Task task) {

        try {
            if (!Files.exists(pathToFile)) {
                Files.createFile(pathToFile);
                Files.writeString(pathToFile, "id,type,name,status,description,epic\n", StandardOpenOption.APPEND);
            }

            String content = Files.readString(pathToFile);
            String[] lines = content.split("\n");
            StringJoiner joiner = new StringJoiner("\n");

            for (String line : lines) {
                if (line.trim().startsWith("id")) {
                    joiner.add(line.trim());
                    continue;
                }

                if (line.trim().startsWith(String.valueOf(task.getId()))) {
                    return;
                }

                if (line.trim().isBlank()) {
                    joiner.add(toString(task)).add("");
                } else {
                    joiner.add(line.trim());
                }
            }

            Files.writeString(pathToFile, joiner.toString());

        } catch (FileAlreadyExistsException e) {
            System.out.println("File " + e.getFile() + " already exists");
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public static TaskManager loadFromFile(Path filepath) throws IOException {
        FileBackendTasksManager tasksManager = new FileBackendTasksManager(filepath);

        if (Files.exists(filepath)) {
            String content = Files.readString(filepath);
            String[] lines = content.split("\n");
            boolean isHistory = false;

            for (String line : lines) {
                if (line.trim().startsWith("id")) continue;
                if (line.trim().isBlank()) {
                    isHistory = true;
                    continue;
                }
                if (isHistory) {
                    List<Long> history = historyFromString(line);

                    for (Long id : history) {
                        tasksManager.historyManager.add(id);
                    }
                    break;
                }

                Task task = fromString(line.trim());
                tasksManager.updateTask(task, task.getId());
            }
        } else {
            throw new ManagerLoadException("File does not exist");
        }

        return tasksManager;
    }

    @Override
    public void addTask(Task task) throws IOException {
        super.addTask(task);
        save(task);
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
        return manager.getHistory().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static List<Long> historyFromString(String value) {
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .toList();
    }
}
