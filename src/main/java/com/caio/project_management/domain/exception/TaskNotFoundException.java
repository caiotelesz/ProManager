package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class TaskNotFoundException extends RequestException {

    public TaskNotFoundException(String id) {
        super("Task not found", "Task not found: " + id);
    }
}
