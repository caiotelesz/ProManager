package com.caio.project_management.infrastructure.controller;

import com.caio.project_management.domain.entity.Task;
import com.caio.project_management.domain.service.TaskService;
import com.caio.project_management.infrastructure.dto.SaveTaskDataDTO;
import com.caio.project_management.infrastructure.dto.TaskDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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


}
