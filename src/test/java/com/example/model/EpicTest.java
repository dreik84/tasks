package com.example.model;

import com.example.service.InMemoryTasksManager;
import com.example.service.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void testEpicStatus() throws IOException {
        TaskManager taskManager = new InMemoryTasksManager();

        Epic epic = new Epic("Выучить английский",
                "Нужно выучить английский язык",
                Status.NEW);

        assertEquals(Status.NEW, epic.getStatus());

        Subtask subtask1 = new Subtask("Купить учебник",
                "Необходимо приобрести учебник по английскому языку",
                Status.NEW,
                epic.getId());

        Subtask subtask2 = new Subtask("Выучить грамматику языка",
                "Начать изучать грамматику английского языка",
                Status.NEW,
                epic.getId());

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);

        assertEquals(Status.NEW, epic.getStatus());

        taskManager.updateStatus(subtask1, Status.IN_PROCESS);

        assertEquals(Status.IN_PROCESS, epic.getStatus());

        taskManager.updateStatus(subtask1, Status.DONE);

        assertEquals(Status.IN_PROCESS, epic.getStatus());

        taskManager.updateStatus(subtask1, Status.DONE);
        taskManager.updateStatus(subtask2, Status.DONE);

        assertEquals(Status.DONE, epic.getStatus());
    }
}
