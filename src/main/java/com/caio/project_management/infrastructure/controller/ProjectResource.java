package com.caio.project_management.infrastructure.controller;

import com.caio.project_management.domain.entity.Project;
import com.caio.project_management.domain.service.ProjectService;
import com.caio.project_management.infrastructure.dto.ProjectDTO;
import com.caio.project_management.infrastructure.dto.SaveProjectDataDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.caio.project_management.infrastructure.controller.RestResource.PATH_PROJECTS;

@Controller
@RequestMapping(PATH_PROJECTS)
@RequiredArgsConstructor
public class ProjectResource {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> saveProject(@RequestBody @Valid SaveProjectDataDTO saveProjectData) {

        Project project = projectService.saveProject(saveProjectData);

        return ResponseEntity
                .created(URI.create(PATH_PROJECTS + "/" + project.getId()))
                .body(ProjectDTO.create(project));
    }

    // GET project/id
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> loadProject(@PathVariable("id") String projectId) {

        Project project = projectService.loadProject(projectId);

        return ResponseEntity
                .ok(ProjectDTO.create(project));
    }

    // DELETE project/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") String projectId) {

        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
