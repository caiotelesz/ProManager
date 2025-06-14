package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.enums.TaskStatus;
import com.caio.project_management.domain.exception.TaskNotFoundException;
import com.caio.project_management.domain.repository.TaskRepository;
import com.caio.project_management.infrastructure.dto.SaveTaskDataDTO;
import com.caio.project_management.infrastructure.dto.TaskDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task saveTask(SaveTaskDataDTO saveTaskData) {
        Task task = Task
                .builder()
                .title(saveTaskData.getTitle())
                .description(saveTaskData.getDescription())
                .numberOfDays(saveTaskData.getNumberOfDays())
                .status(TaskStatus.PENDING)
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
}
