package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    TaskManager taskManager;
    Task task;
    Subtask subtask1;
    Subtask subtask2;
    Epic epic;

    @BeforeEach
    void setUp() throws IOException {
        taskManager = new InMemoryTasksManager();

        task = new Task("Починить забор",
                "Нужно отремонтировать забор с использование подручного инструмента",
                Status.NEW);

        epic = new Epic("Выучить английский",
                "Нужно выучить английский язык",
                Status.NEW);

        subtask1 = new Subtask("Купить учебник",
                "Необходимо приобрести учебник по английскому языку",
                Status.NEW,
                epic.getId());

        subtask2 = new Subtask("Выучить грамматику языка",
                "Начать изучать грамматику английского языка",
                Status.NEW,
                epic.getId());

        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);
    }

    @Test
    void getHistory() {
        assertTrue(taskManager.getHistory().isEmpty());

        taskManager.getTaskById(task.getId());

        assertFalse(taskManager.getHistory().isEmpty());
        assertEquals(task.getId(), taskManager.getHistory().get(0));

        taskManager.getTaskById(task.getId());
        taskManager.getSubtaskById(subtask1.getId());
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getEpicById(epic.getId());

        assertEquals(4, taskManager.getHistory().size());
    }

    @Test
    void getTasks() {
        Task expected = task;
        Task actual = taskManager.getTasks().get(task.getId());

        assertEquals(1, taskManager.getTasks().size());
        assertEquals(expected, actual);
    }

    @Test
    void getSubtasks() {
        Subtask expected = subtask1;
        Subtask actual = taskManager.getSubtasks().get(subtask1.getId());

        assertEquals(2, taskManager.getSubtasks().size());
        assertEquals(expected, actual);
    }

    @Test
    void getEpics() {
        Epic expected = epic;
        Epic actual = taskManager.getEpics().get(epic.getId());

        assertEquals(1, taskManager.getEpics().size());
        assertEquals(expected, actual);
    }

    @Test
    void removeAllTasks() {
        assertFalse(taskManager.getTasks().isEmpty());
        taskManager.removeAllTasks();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    void removeAllSubtasks() {
        assertFalse(taskManager.getSubtasks().isEmpty());
        taskManager.removeAllSubtasks();
        assertTrue(taskManager.getSubtasks().isEmpty());
    }

    @Test
    void removeAllEpics() {
        assertFalse(taskManager.getEpics().isEmpty());
        taskManager.removeAllEpics();
        assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void getTaskById() {
        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertTrue(taskManager.getHistory().contains(task.getId()));
    }

    @Test
    void getSubtaskById() {
        assertEquals(subtask1, taskManager.getSubtaskById(subtask1.getId()));
        assertTrue(taskManager.getHistory().contains(subtask1.getId()));
    }

    @Test
    void getEpicById() {
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertTrue(taskManager.getHistory().contains(epic.getId()));
    }

    @Test
    void addTask() throws IOException {
        TaskManager taskManager = new InMemoryTasksManager();

        assertTrue(taskManager.getTasks().isEmpty());
        assertTrue(taskManager.getSubtasks().isEmpty());
        assertTrue(taskManager.getEpics().isEmpty());

        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);

        assertEquals(1, taskManager.getTasks().size());
        assertEquals(2, taskManager.getSubtasks().size());
        assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    void updateTask() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void updateEpic() {
    }

    @Test
    void removeTaskById() {
    }

    @Test
    void removeSubtaskById() {
    }

    @Test
    void removeEpicById() {
    }

    @Test
    void updateStatus() {
    }
}