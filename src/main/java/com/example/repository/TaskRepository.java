package com.example.repository;

import com.example.domain.Task;

public interface TaskRepository {
    String save(Task task);
    Task findById(String id);
    void update(String id, Task task);
    int count();
}
