package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class InvalidProjectStatusException extends RequestException {

    public InvalidProjectStatusException(String status) {
        super("Project not found", "Project not found: " + status);
    }
}
