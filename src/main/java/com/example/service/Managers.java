package com.example.service;

import java.nio.file.Path;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTasksManager();
    }

    public static FileBackendTasksManager getFileBackendManager(Path filePath) {
        return new FileBackendTasksManager(filePath);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
