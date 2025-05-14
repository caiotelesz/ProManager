package com.caio.project_management.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SaveProjectDataDTO {

    @NotNull
    @Size(min = 1, max = 36)
    private final String name;

    @NotNull
    @Size(min = 1, max = 150)
    private final String description;

    @NotNull
    private final LocalDate initialDate;

    @NotNull
    private final LocalDate finalDate;

    private final String status;
}
