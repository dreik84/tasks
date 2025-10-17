package com.example.service;

import java.util.List;

public interface HistoryManager {

    void add(long id);

    void remove(long id);

    List<Long> getHistory();
}
