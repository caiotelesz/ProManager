package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Optional;

@Data
public class TaskDTO {
    private final String id;
    private final String title;
    private final String description;
    private final Integer numberOfDays;
    private final TaskStatus status;
    private final ProjectDTO project;
    private final MemberDTO assignedMember;

    public static TaskDTO create(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus(),
                Optional.ofNullable(task.getProject()).map(ProjectDTO::create).orElse(null),
                Optional.ofNullable(task.getAssignedMember()).map(MemberDTO::create).orElse(null)
        );
    }
}
