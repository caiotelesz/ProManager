package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.entity.Project;
import com.caio.project_management.domain.enums.ProjectStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProjectDTO {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDate initialDate;
    private final LocalDate finalDate;
    private final ProjectStatus status;
    private final Set<String> membersIds;

    public static ProjectDTO create(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getInitialDate(),
                project.getFinalDate(),
                project.getStatus(),
                Optional
                        .ofNullable(project.getMembers())
                        .orElse(List.of())
                        .stream()
                        .map(Member::getId)
                        .collect(Collectors.toSet())
        );
    }
}
