package com.example;

import com.example.model.Epic;
import com.example.model.Status;
import com.example.model.Subtask;
import com.example.model.Task;
import com.example.service.TaskManager;

public class Main {
    public static void main(String[] args) {

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
                epic1);

        Subtask subtask2 = new Subtask("Выучить грамматику языка",
                "Начать изучать грамматику английского языка",
                Status.NEW,
                epic1);

        Epic epic2 = new Epic("Построить дом",
                "Нужно построить жилой дом",
                Status.NEW);

        Subtask subtask3 = new Subtask("Заказать стройматериалы",
                "Необходимо заказать инструменты и материалы для строительства",
                Status.NEW,
                epic2);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3, epic2);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        System.out.println();

        taskManager.updateStatus(task1, Status.IN_PROCESS);
        taskManager.updateStatus(task2, Status.IN_PROCESS);

        task1.setStatus(Status.IN_PROCESS);
        task2.setStatus(Status.IN_PROCESS);
        subtask1.setStatus(Status.IN_PROCESS);
        subtask2.setStatus(Status.IN_PROCESS);
        subtask2.setStatus(Status.DONE);
        epic1.updateStatus();
        epic2.updateStatus();

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
        System.out.println();

        taskManager.removeTaskById(task1.getId());
        taskManager.removeSubtaskById(subtask1.getId());
        taskManager.removeEpicById(epic1.getId());

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());
    }
}
