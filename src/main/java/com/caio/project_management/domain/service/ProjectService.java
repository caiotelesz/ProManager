package com.caio.project_management.domain.service;

import com.caio.project_management.domain.entity.Project;
import com.caio.project_management.domain.enums.ProjectStatus;
import com.caio.project_management.domain.exception.ProjectNotFoundException;
import com.caio.project_management.domain.repository.ProjectRepository;
import com.caio.project_management.infrastructure.dto.SaveProjectDataDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project saveProject(SaveProjectDataDTO saveProjectData) {

        Project project = Project
                .builder()
                .name(saveProjectData.getName())
                .description(saveProjectData.getDescription())
                .initialDate(saveProjectData.getInitialDate())
                .finalDate(saveProjectData.getFinalDate())
                .status(ProjectStatus.PENDING)
                .build();

        projectRepository.save(project);

        log.info("Saved project : {}", project);

        return project;
    }

    public Project loadProject(String projectId) {
        Project project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        log.info("Loaded project : {}", project);

        return project;
    }

    public void deleteProject(String projectId) {

        Project project = loadProject(projectId);
        projectRepository.delete(project);

        log.info("Deleted project : {}", project);
    }
}
