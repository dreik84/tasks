package com.example;

public class Subtask extends Task {

    private Long epicId;

    public Subtask(String name, String description, Status status, Long epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }
}
