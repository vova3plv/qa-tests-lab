package com.example.integration;

import com.example.domain.Task;
import com.example.domain.User;
import com.example.repository.InMemoryTaskRepository;
import com.example.repository.TaskRepository;
import com.example.service.AccessControlService;
import com.example.service.TaskManagementService;
import com.example.service.TaskValidationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskManagementIntegrationTest {

    @Test
    void IT_01_createTask_validOwner_shouldPersist() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        TaskManagementService service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                repo
        );

        User owner = new User("u1", "OWNER");
        Task task = new Task("Title", "Desc", LocalDate.now().plusDays(1), "u1");

        String id = service.createTask(owner, task);

        assertNotNull(id);
        assertEquals(1, repo.count());
        assertNotNull(repo.findById(id));
        assertEquals("Title", repo.findById(id).getTitle());
    }

    @Test
    void IT_02_createTask_invalidTask_shouldNotPersist() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        TaskManagementService service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                repo
        );

        User owner = new User("u1", "OWNER");
        Task invalid = new Task("", "Desc", LocalDate.now().plusDays(1), "u1");

        assertThrows(IllegalArgumentException.class, () -> service.createTask(owner, invalid));
        assertEquals(0, repo.count());
    }

    @Test
    void IT_03_createTask_observer_shouldNotPersist() {
        InMemoryTaskRepository repo = new InMemoryTaskRepository();
        TaskManagementService service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                repo
        );

        User observer = new User("u2", "OBSERVER");
        Task task = new Task("Title", "Desc", LocalDate.now().plusDays(1), "u2");

        assertThrows(SecurityException.class, () -> service.createTask(observer, task));
        assertEquals(0, repo.count());
    }

    @Test
    void IT_04_mockRepo_noPermissions_repoNotCalled() {
        TaskRepository mockRepo = Mockito.mock(TaskRepository.class);
        TaskManagementService service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                mockRepo
        );

        User observer = new User("u2", "OBSERVER");
        Task task = new Task("Title", "Desc", LocalDate.now().plusDays(1), "u2");

        assertThrows(SecurityException.class, () -> service.createTask(observer, task));
        verifyNoInteractions(mockRepo);
    }

    @Test
    void IT_05_spyRepo_saveIsCalledAndDataIsStored() {
        InMemoryTaskRepository realRepo = new InMemoryTaskRepository();
        InMemoryTaskRepository spyRepo = spy(realRepo);

        TaskManagementService service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                spyRepo
        );

        User owner = new User("u1", "OWNER");
        Task task = new Task("Title", "Desc", LocalDate.now().plusDays(1), "u1");

        String id = service.createTask(owner, task);

        verify(spyRepo, times(1)).save(any(Task.class));
        assertNotNull(realRepo.findById(id));
    }
}
