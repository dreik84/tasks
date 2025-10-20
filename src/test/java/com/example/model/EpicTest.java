package com.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void testEpicStatus() {
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

        assertEquals(Status.NEW, epic.getStatus());

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);

        assertEquals(Status.DONE, epic.getStatus());
    }

}