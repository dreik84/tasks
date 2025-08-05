package com.example;

import java.util.HashMap;
import java.util.Map;

public class TaskManager {

    private static Long id = 0L;

    private Map<Long, String> tasks;
    private Map<Long, String> subTasks;
    private Map<Long, String> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subTasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static Long generateId() {
        return ++id;
    }

    public Map<Long, String> getTasks() {
        return tasks;
    }

    public Map<Long, String> getSubTasks() {
        return subTasks;
    }

    public Map<Long, String> getEpics() {
        return epics;
    }

    public void removeTasks() {
        for (var key : tasks.keySet()) {
            tasks.remove(key);
        }
    }

    public void removeSubTasks() {
        subTasks = new HashMap<>();
    }

    public void removeEpics() {
        epics = new HashMap<>();
    }
}
