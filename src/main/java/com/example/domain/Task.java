package com.example.domain;

import java.time.LocalDate;

public class Task {

    private final String title;
    private final String description;
    private final LocalDate deadline;
    private final String ownerId;

    public Task(String title, String description, LocalDate deadline, String ownerId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getOwnerId() {
        return ownerId;
    }
}
