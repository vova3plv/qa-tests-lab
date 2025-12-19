package com.example.api.service;

import com.example.api.model.CreateTaskRequest;
import com.example.api.model.TaskDto;
import com.example.api.model.UpdateTaskRequest;
import com.example.api.store.TaskStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Service
public class TaskApiService {

    private final TaskStore store = new TaskStore();

    public TaskDto create(String role, String userId, CreateTaskRequest req) {
        requireOwner(role);
        validateCreate(req);

        String id = UUID.randomUUID().toString();
        TaskDto task = new TaskDto(id,
                req.getTitle().trim(),
                req.getDescription() == null ? "" : req.getDescription(),
                parseDate(req.getDeadline()),
                userId);
        store.put(task);
        return task;
    }

    public TaskDto get(String id) {
        return store.get(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public TaskDto update(String role, String userId, String id, UpdateTaskRequest req) {
        requireOwner(role);
        TaskDto existing = get(id);
        if (!existing.getOwnerId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can edit only your own task");
        }

        // Apply updates + validate
        String newTitle = req.getTitle() == null ? existing.getTitle() : req.getTitle();
        String newDesc = req.getDescription() == null ? existing.getDescription() : req.getDescription();
        String newDeadlineStr = req.getDeadline() == null ? existing.getDeadline().toString() : req.getDeadline();

        validateCommon(newTitle, newDesc, newDeadlineStr);

        TaskDto updated = new TaskDto(id,
                newTitle.trim(),
                newDesc,
                parseDate(newDeadlineStr),
                existing.getOwnerId());
        store.put(updated);
        return updated;
    }

    private void requireOwner(String role) {
        if (role == null || !role.equalsIgnoreCase("OWNER")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only OWNER can perform this action");
        }
    }

    private void validateCreate(CreateTaskRequest req) {
        if (req == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body is required");
        }
        validateCommon(req.getTitle(), req.getDescription(), req.getDeadline());
    }

    private void validateCommon(String title, String description, String deadline) {
        if (title == null || title.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "title must not be empty");
        }
        if (description != null && description.length() > 500) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "description must be <= 500 chars");
        }
        LocalDate d = parseDate(deadline);
        if (d.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deadline must not be in the past");
        }
    }

    private LocalDate parseDate(String deadline) {
        if (deadline == null || deadline.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deadline is required (YYYY-MM-DD)");
        }
        try {
            return LocalDate.parse(deadline);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deadline must be ISO date YYYY-MM-DD");
        }
    }
}
