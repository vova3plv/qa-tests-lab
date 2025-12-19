package com.example.service;

import com.example.domain.Task;
import com.example.domain.User;
import com.example.repository.TaskRepository;

public class TaskManagementService {

    private final TaskValidationService validationService;
    private final AccessControlService accessControlService;
    private final TaskRepository repository;

    public TaskManagementService(TaskValidationService validationService,
                                 AccessControlService accessControlService,
                                 TaskRepository repository) {
        this.validationService = validationService;
        this.accessControlService = accessControlService;
        this.repository = repository;
    }

    public String createTask(User user, Task task) {
        if (!validationService.isValid(task)) {
            throw new IllegalArgumentException("Invalid task");
        }
        if (!accessControlService.canEditTask(user, task)) {
            throw new SecurityException("No permissions");
        }
        return repository.save(task);
    }
}
