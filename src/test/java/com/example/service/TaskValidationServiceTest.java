package com.example.service;

import com.example.domain.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidationServiceTest {

    private final TaskValidationService service = new TaskValidationService();

    @Test
    void validTask_shouldReturnTrue() {
        Task task = new Task("Test task", "Valid description", LocalDate.now().plusDays(1), "user1");
        assertTrue(service.isValid(task));
    }

    @Test
    void emptyTitle_shouldReturnFalse() {
        Task task = new Task("", "Description", LocalDate.now().plusDays(1), "user1");
        assertFalse(service.isValid(task));
    }

    @Test
    void pastDeadline_shouldReturnFalse() {
        Task task = new Task("Task", "Description", LocalDate.now().minusDays(1), "user1");
        assertFalse(service.isValid(task));
    }
}
