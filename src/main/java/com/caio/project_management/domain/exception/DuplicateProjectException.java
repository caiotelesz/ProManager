package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class DuplicateProjectException extends RequestException {

    public DuplicateProjectException(String name) {
        super("Name already exists", "This name already exists in project: " + name);
    }
}
