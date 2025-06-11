package com.caio.project_management.domain.exception;

import com.caio.project_management.infrastructure.exception.RequestException;

public class MemberNotFoundException extends RequestException {

    public MemberNotFoundException(String id) {
        super("Member not found", "Member not found: " + id);
    }
}
