package com.example.service;

import com.example.domain.Task;
import com.example.domain.User;

public class AccessControlService {

    public boolean canEditTask(User user, Task task) {
        if (user == null || task == null) {
            return false;
        }

        if (!"OWNER".equals(user.getRole())) {
            return false;
        }

        return user.getId().equals(task.getOwnerId());
    }
}
