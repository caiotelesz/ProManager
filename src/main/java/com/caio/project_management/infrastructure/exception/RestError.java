package com.caio.project_management.infrastructure.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestError {
    private final String errorCode;
    private final String errorMessage;
    private final int stautus;
    private String path;
}
