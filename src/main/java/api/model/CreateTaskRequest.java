package com.example.api.model;

public class CreateTaskRequest {
    private String title;
    private String description;
    private String deadline; // ISO date: YYYY-MM-DD

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
}
