package com.example.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    @Test
    void add() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        assertTrue(historyManager.getHistory().isEmpty());

        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(3);
        historyManager.add(4);
        historyManager.add(5);
        historyManager.add(6);
        historyManager.add(7);
        historyManager.add(8);
        historyManager.add(9);
        historyManager.add(10);

        assertEquals(10, historyManager.getHistory().size());

        historyManager.add(11);

        assertEquals(10, historyManager.getHistory().size());
        assertEquals(2, historyManager.getHistory().get(0));

        historyManager.add(5);

        assertEquals(10, historyManager.getHistory().size());
    }

    @Test
    void remove() {
        HistoryManager historyManager = new InMemoryHistoryManager();

        assertTrue(historyManager.getHistory().isEmpty());

        historyManager.add(1);

        assertFalse(historyManager.getHistory().isEmpty());

        historyManager.remove(1);

        assertTrue(historyManager.getHistory().isEmpty());
        assertThrows(IllegalArgumentException.class, () -> historyManager.remove(-1));
    }

    @Test
    void getHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        List<Long> expected = List.of();
        List<Long> actual = historyManager.getHistory();

        assertEquals(expected, actual);

        historyManager.add(1);
        historyManager.add(2);
        historyManager.add(3);

        expected = List.of(1L, 2L, 3L);
        actual = historyManager.getHistory();

        assertEquals(expected, actual);
    }
}