package com.caio.project_management.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveTaskDataDTO {
    @NotNull(message = "Title cannot be null")
    @Size(min = 1, max = 80, message = "Invalid title")
    private final String title;

    @NotNull(message = "Description cannot be null")
    @Size(min = 1, max = 150, message = "Invalid description")
    private final String description;

    @NotNull(message = "Number of days cannot be null")
    @Positive(message = "Number of days must be positive")
    private final Integer numberOfDays;

    private final String status;
}
