package com.example.service;

import com.example.domain.Task;

import java.time.LocalDate;

public class TaskValidationService {

    public boolean isValid(Task task) {
        if (task == null) {
            return false;
        }

        if (task.getTitle() == null || task.getTitle().isBlank()) {
            return false;
        }

        if (task.getDescription() != null && task.getDescription().length() > 500) {
            return false;
        }

        if (task.getDeadline() != null && task.getDeadline().isBefore(LocalDate.now())) {
            return false;
        }

        return true;
    }
}
