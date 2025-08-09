package com.example;

public class Main {
    public static void main(String[] args) {

        Task task1 = new Task("Починить забор",
                "Нужно отремонтировать забор с использование подручного инструмента",
                Status.NEW);

        Task taks2 = new Task("Вынести мусор",
                "Небходимо выбрость мусор в специальный контейнер",
                Status.NEW);

        Epic epic1 = new Epic("Выучить английский",
                "Нужно выучить английский язык",
                Status.NEW);

        Subtask subtask1 = new Subtask("Купить учебник",
                "Необходимо приобрести учебник по английскому языку",
                Status.NEW,
                epic1);

        Subtask subtask2 = new Subtask("Выучить граматику языка",
                "Начать изучать граматику английского языка",
                Status.NEW,
                epic1);

        Epic epic2 = new Epic("Построить дом",
                "Нужно построить жилой дом",
                Status.NEW);

        Subtask subtask3 = new Subtask("Заказать стройматериаллы",
                "Необходимо заказать инструменты и материаллы для строительства",
                Status.NEW,
                epic2);

        TaskManager taskManager = new TaskManager();
        taskManager.addTask(task1);
        taskManager.addTask(taks2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3, epic2);

        System.out.println(taskManager.getTasks());
        System.out.println(taskManager.getEpics());
        System.out.println(taskManager.getSubTasks());

        task1.status = Status.IN_PROCESS;
        taks2.status = Status.IN_PROCESS;
    }
}
