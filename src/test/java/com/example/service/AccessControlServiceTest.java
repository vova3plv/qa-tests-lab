package com.example.service;

import com.example.domain.Task;
import com.example.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccessControlServiceTest {

    private final AccessControlService service = new AccessControlService();

    @Test
    void ownerCanEditOwnTask() {
        User user = new User("user1", "OWNER");
        Task task = new Task("Task", "Desc", LocalDate.now(), "user1");
        assertTrue(service.canEditTask(user, task));
    }

    @Test
    void observerCannotEditTask() {
        User user = new User("user2", "OBSERVER");
        Task task = new Task("Task", "Desc", LocalDate.now(), "user2");
        assertFalse(service.canEditTask(user, task));
    }

    @Test
    void ownerCannotEditForeignTask() {
        User user = new User("user1", "OWNER");
        Task task = new Task("Task", "Desc", LocalDate.now(), "user2");
        assertFalse(service.canEditTask(user, task));
    }
}
