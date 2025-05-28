package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Project;
import com.caio.project_management.domain.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDTO {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalDate;
    private final ProjectStatus status;

    public static ProjectDTO create(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate(),
                project.getStatus()
        );
    }
}
