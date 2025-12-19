package com.example.e2e.steps;

import com.example.domain.Task;
import com.example.domain.User;
import com.example.repository.InMemoryTaskRepository;
import com.example.service.AccessControlService;
import com.example.service.TaskManagementService;
import com.example.service.TaskValidationService;
import io.cucumber.java.en.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskSmokeSteps {

    private InMemoryTaskRepository repo;
    private TaskManagementService service;

    private User user;
    private Task task;

    private String createdId;
    private Exception capturedError;

    @Given("the task management system is initialized")
    public void initSystem() {
        repo = new InMemoryTaskRepository();
        service = new TaskManagementService(
                new TaskValidationService(),
                new AccessControlService(),
                repo
        );

        createdId = null;
        capturedError = null;
    }

    @Given("a user {string} with role {string}")
    public void aUserWithRole(String userId, String role) {
        user = new User(userId, role);
    }

    @Given("a task with title {string} and description {string} and deadline offset {int} days and ownerId {string}")
    public void aTask(String title, String desc, int days, String ownerId) {
        String realDesc = desc;
        if ("LONG_501".equals(desc)) {
            realDesc = "a".repeat(501);
        }

        LocalDate deadline = LocalDate.now().plusDays(days);
        task = new Task(title, realDesc, deadline, ownerId);
    }

    @When("the user tries to create the task")
    public void createTask() {
        try {
            createdId = service.createTask(user, task);
        } catch (Exception e) {
            capturedError = e;
        }
    }

    @Then("the task is created successfully")
    public void taskCreated() {
        assertNotNull(createdId);
        assertNull(capturedError);
    }

    @Then("creation fails with {string}")
    public void creationFails(String errorType) {
        assertNotNull(capturedError);

        if ("SecurityException".equals(errorType)) {
            assertTrue(capturedError instanceof SecurityException);
        } else if ("IllegalArgumentException".equals(errorType)) {
            assertTrue(capturedError instanceof IllegalArgumentException);
        } else {
            fail("Unknown expected error type: " + errorType);
        }
    }

    @Then("repository count should be {int}")
    public void repoCount(int expected) {
        assertEquals(expected, repo.count());
    }
}
