package com.example;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;
import com.example.service.FileBackendTasksManager;
import com.example.service.Managers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        Task task1 = new Task("Починить забор",
                "Нужно отремонтировать забор с использование подручного инструмента",
                Status.NEW);

        Task task2 = new Task("Вынести мусор",
                "Необходимо выбросить мусор в специальный контейнер",
                Status.NEW);

        Epic epic1 = new Epic("Выучить английский",
                "Нужно выучить английский язык",
                Status.NEW);

        Subtask subtask1 = new Subtask("Купить учебник",
                "Необходимо приобрести учебник по английскому языку",
                Status.NEW,
                epic1.getId());

        Subtask subtask2 = new Subtask("Выучить грамматику языка",
                "Начать изучать грамматику английского языка",
                Status.NEW,
                epic1.getId());

        Epic epic2 = new Epic("Построить дом",
                "Нужно построить жилой дом",
                Status.NEW);

        Subtask subtask3 = new Subtask("Заказать стройматериалы",
                "Необходимо заказать инструменты и материалы для строительства",
                Status.NEW,
                epic2.getId());

        Path filePath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "tasks.csv")
                .normalize().toAbsolutePath();
        System.out.println(filePath);
        FileBackendTasksManager taskManager = Managers.getFileBackendManager(filePath);
        FileBackendTasksManager.loadFromFile(filePath);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(epic1);
        taskManager.addTask(subtask1);
        taskManager.addTask(subtask2);
        taskManager.addTask(epic2);
        taskManager.addTask(subtask3);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();

        taskManager.updateStatus(task1, Status.IN_PROCESS);
        taskManager.updateStatus(task2, Status.IN_PROCESS);
        taskManager.updateStatus(subtask1, Status.IN_PROCESS);
        taskManager.updateStatus(subtask2, Status.IN_PROCESS);
        taskManager.updateStatus(subtask3, Status.DONE);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();

        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubtaskById(subtask1.getId());
        taskManager.removeEpicById(epic1.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubtasks());
        System.out.println();

        taskManager.getTaskById(task2.getId());
        taskManager.getSubtaskById(subtask3.getId());
        taskManager.getEpicById(epic2.getId());

        System.out.println(taskManager.getHistory());
        System.out.println();

        System.out.println(taskManager.toString(task2));
        System.out.println(taskManager.toString(subtask3));
        System.out.println(taskManager.toString(epic2));
    }
}
