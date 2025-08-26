package com.caio.project_management.infrastructure.controller;

import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.service.TaskService;
import com.caio.project_management.infrastructure.dto.SaveTaskDataDTO;
import com.caio.project_management.infrastructure.dto.TaskDTO;
import com.caio.project_management.infrastructure.util.SortProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.caio.project_management.infrastructure.controller.RestResource.PATH_TASKS;

@Controller
@RequestMapping(PATH_TASKS)
@RequiredArgsConstructor
public class TaskResource {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> saveTask(@RequestBody @Valid SaveTaskDataDTO saveTaskData) {
        Task task = taskService.saveTask(saveTaskData);

        return ResponseEntity
                .created(URI.create(PATH_TASKS + "/" + task.getId()))
                .body(TaskDTO.create(task));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> loadTask(@PathVariable("id") String taskId) {
        Task task = taskService.loadTask(taskId);

        return ResponseEntity.ok(TaskDTO.create(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String taskId) {
        taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable("id") String taskId,
            @RequestBody @Valid SaveTaskDataDTO saveTaskData
    ) {
        Task task = taskService.updateTask(taskId, saveTaskData);

        return ResponseEntity.ok(TaskDTO.create(task));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findTasks(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "memberId", required = false) String memberId,
            @RequestParam(value = "status", required = false) String statusStr,
            @RequestParam(value = "partialTitle", required = false) String partialTitle,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "sort", required = false)SortProperties properties
            ) {
        Page<Task> task = taskService.findTasks(
                projectId,
                memberId,
                statusStr,
                partialTitle,
                page,
                direction,
                properties.getSortProperties()
        );

        return ResponseEntity.ok(task.stream().map(TaskDTO::create).toList());
    }
}
