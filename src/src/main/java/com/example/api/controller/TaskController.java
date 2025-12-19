package com.example.api.controller;

import com.example.api.model.CreateTaskRequest;
import com.example.api.model.TaskDto;
import com.example.api.model.UpdateTaskRequest;
import com.example.api.service.TaskApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskApiService service;

    public TaskController(TaskApiService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @RequestHeader(value = "X-Role", required = false) String role,
            @RequestHeader(value = "X-UserId", required = false) String userId,
            @RequestBody CreateTaskRequest body
    ) {
        if (userId == null || userId.trim().isEmpty()) {
            // keep it simple: userId is mandatory
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        TaskDto created = service.create(role, userId, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable String id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(
            @RequestHeader(value = "X-Role", required = false) String role,
            @RequestHeader(value = "X-UserId", required = false) String userId,
            @PathVariable String id,
            @RequestBody UpdateTaskRequest body
    ) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.BAD_REQUEST, "X-UserId header required");
        }
        return service.update(role, userId, id, body);
    }
}
