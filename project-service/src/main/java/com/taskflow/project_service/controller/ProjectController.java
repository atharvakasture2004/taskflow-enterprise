package com.taskflow.project_service.controller;

import com.taskflow.project_service.dto.CreateProjectRequest;
import com.taskflow.project_service.dto.CreateTaskRequest;
import com.taskflow.project_service.model.Project;
import com.taskflow.project_service.model.Task;
import com.taskflow.project_service.service.ProjectService;
import jakarta.validation.Valid;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    public Project createProject(
            @Valid
            @RequestBody CreateProjectRequest request) {

        return service.createProject(request);
    }

    @GetMapping
    public List<Project> getProjects() {

        return service.getProjects();
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(
            @PathVariable @NonNull UUID projectId) {

        return service.getProjectById(projectId);
    }

    @PostMapping("/{projectId}/tasks")
    public Task createTask(
            @PathVariable UUID projectId,
            @Valid
            @RequestBody CreateTaskRequest request) {

        return service.createTask(
                projectId,
                request
        );
    }

    @GetMapping("/{projectId}/tasks")
    public List<Task> getTasks(
            @PathVariable UUID projectId) {

        return service.getTasks(projectId);
    }
}