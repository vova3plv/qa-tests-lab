package com.example.repository;

import com.example.domain.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<String, Task> storage = new HashMap<>();

    @Override
    public String save(Task task) {
        String id = UUID.randomUUID().toString();
        storage.put(id, task);
        return id;
    }

    @Override
    public Task findById(String id) {
        return storage.get(id);
    }

    @Override
    public void update(String id, Task task) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Task not found: " + id);
        }
        storage.put(id, task);
    }

    @Override
    public int count() {
        return storage.size();
    }
}
