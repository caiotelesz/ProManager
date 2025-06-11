package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class DuplicateMemberException extends RequestException {

    public DuplicateMemberException(String email) {
        super("E-mail already exists", "This e-mail already exists in member: " + email);
    }
}
