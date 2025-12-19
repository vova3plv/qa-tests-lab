package com.example.api.store;

import com.example.api.model.TaskDto;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class TaskStore {
    private final Map<String, TaskDto> tasks = new ConcurrentHashMap<>();

    public void put(TaskDto task) {
        tasks.put(task.getId(), task);
    }

    public Optional<TaskDto> get(String id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public boolean exists(String id) {
        return tasks.containsKey(id);
    }
}
