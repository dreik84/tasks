package com.example;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private Map<Long, String> tasks = new HashMap<>();
    private Map<Long, String> subTasks = new HashMap<>();
    private Map<Long, String> epics = new HashMap<>();
    private static Long id = 0L;

    public static Long generateId() {
        return ++id;
    }
}
