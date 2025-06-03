package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class ProjectNotFoundException extends RequestException {

    public ProjectNotFoundException(String id) {
        super("Project not found", "Project not found: " + id);
    }
}
