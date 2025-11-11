package com.example.service;

import com.example.model.Status;
import com.example.model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackendTasksManagerTest {

    @Test
    void loadFromFile() throws IOException {
        Path loadFile = Paths.get("src/test/resources/tasksForLoad.csv");
        TaskManager tasksManager = FileBackendTasksManager.loadFromFile(loadFile);

        assertEquals(2, tasksManager.getTasks().size());
        assertEquals(3, tasksManager.getSubtasks().size());
        assertEquals(2, tasksManager.getEpics().size());

        List<Long> expected = List.of(2L, 4L, 6L);
        List<Long> actual = tasksManager.getHistory();

        assertEquals(expected, actual);
    }

    @Test
    void addTask() throws IOException {
        Path loadFile = Paths.get("src/test/resources/tasksForAdd.csv");
        TaskManager tasksManager = FileBackendTasksManager.loadFromFile(loadFile);

        Task task = new Task("Помыть посуду", "Необходимо помыть посуду в раковине", Status.NEW);

        tasksManager.addTask(task);

        tasksManager = FileBackendTasksManager.loadFromFile(loadFile);

        assertEquals(task, tasksManager.getTaskById(task.getId()));

        System.out.println(tasksManager.getTasks());
    }
}