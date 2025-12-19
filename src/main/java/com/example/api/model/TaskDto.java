package com.example.api.model;

import java.time.LocalDate;

public class TaskDto {
    private String id;
    private String title;
    private String description;
    private LocalDate deadline;
    private String ownerId;

    public TaskDto() {}

    public TaskDto(String id, String title, String description, LocalDate deadline, String ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.ownerId = ownerId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
}
