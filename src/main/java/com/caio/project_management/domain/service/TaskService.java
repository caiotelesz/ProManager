package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Member;
import com.caio.project_management.domain.entity.Project;
import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.enums.TaskStatus;
import com.caio.project_management.domain.exception.InvalidTaskStatusException;
import com.caio.project_management.domain.exception.TaskNotFoundException;
import com.caio.project_management.domain.repository.TaskRepository;
import com.caio.project_management.infrastructure.dto.SaveTaskDataDTO;
import com.caio.project_management.infrastructure.dto.TaskDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final MemberService memberService;

    @Transactional
    public Task saveTask(SaveTaskDataDTO saveTaskData) {
        Project project = getProjectIfPossible(saveTaskData.getProjectId());
        Member member = getMemberIfPossible(saveTaskData.getMemberId());

        Task task = Task
                .builder()
                .title(saveTaskData.getTitle())
                .description(saveTaskData.getDescription())
                .numberOfDays(saveTaskData.getNumberOfDays())
                .status(TaskStatus.PENDING)
                .project(project)
                .assignedMember(member)
                .build();

        taskRepository.save(task);

        log.info("Saved task {}", task);

        return task;
    }

    public Task loadTask(String taskId) {
        Task task = taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        log.info("Loaded task {}", task);

        return task;
    }

    @Transactional
    public void deleteTask(String taskId) {
        Task task = loadTask(taskId);
        taskRepository.delete(task);

        log.info("Deleted task {}", task);
    }

    @Transactional
    public Task updateTask(String taskId, SaveTaskDataDTO saveTaskData) {
        Project project = getProjectIfPossible(saveTaskData.getProjectId());
        Member member = getMemberIfPossible(saveTaskData.getMemberId());

        Task task = loadTask(taskId);

        task.setTitle(saveTaskData.getTitle());
        task.setDescription(saveTaskData.getDescription());
        task.setNumberOfDays(saveTaskData.getNumberOfDays());
        task.setStatus(convertStringToStatus(saveTaskData.getStatus()));
        task.setProject(project);
        task.setAssignedMember(member);

        log.info("Updated task {}", task);

        return task;
    }

    public Page<Task> findTasks(
            String projectId,
            String memberId,
            String statusStr,
            String partialTitle,
            Integer page,
            String description,
            List<String> properties
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "title");

        return taskRepository.find(
                projectId,
                memberId,
                Optional.ofNullable(statusStr).map(this::convertStringToStatus).orElse(null),
                partialTitle,
                PageRequest.of(Optional.ofNullable(page).orElse(0), 3, sort)
        );
    }

    private TaskStatus convertStringToStatus(String statusStr) {
        try {
            return TaskStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidTaskStatusException(statusStr);
        }
    }

    private Member getMemberIfPossible(String memberId) {
        Member member = null;
        if(!Objects.isNull(memberId)) {
            member = memberService.loadMember(memberId);
        }
        return member;
    }

    private Project getProjectIfPossible(String projectId) {
        Project project = null;
        if (!Objects.isNull(projectId)) {
            project = projectService.loadProject(projectId);
        }
        return project;
    }
}
