package com.caio.project_management.infrastructure.dto;

import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

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
                ProjectDTO.create(task.getProject()),
                MemberDTO.create(task.getAssignedMember())
        );
    }
}
