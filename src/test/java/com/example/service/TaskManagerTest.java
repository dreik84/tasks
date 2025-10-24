package com.example.service;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TaskManagerTest {

    @Test
    void getHistory() throws IOException {
        TaskManager taskManager = new InMemoryTasksManager();

        Task task = new Task("Починить забор",
                "Нужно отремонтировать забор с использование подручного инструмента",
                Status.NEW);

        Epic epic = new Epic("Выучить английский",
                "Нужно выучить английский язык",
                Status.NEW);

        Subtask subtask1 = new Subtask("Купить учебник",
                "Необходимо приобрести учебник по английскому языку",
                Status.NEW,
                epic.getId());

        Subtask subtask2 = new Subtask("Выучить грамматику языка",
                "Начать изучать грамматику английского языка",
                Status.NEW,
                epic.getId());

        taskManager.addTask(task);
        taskManager.addTask(epic);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);
    }

    @Test
    void getTasks() {
    }

    @Test
    void getSubtasks() {
    }

    @Test
    void getEpics() {
    }

    @Test
    void removeAllTasks() {
    }

    @Test
    void removeAllSubtasks() {
    }

    @Test
    void removeAllEpics() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void getSubtaskById() {
    }

    @Test
    void getEpicById() {
    }

    @Test
    void addTask() {
    }

    @Test
    void addSubtask() {
    }

    @Test
    void addEpic() {
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