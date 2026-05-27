package com.taskflow.project_service.controller;

import com.taskflow.project_service.dto.CreateProjectRequest;
import com.taskflow.project_service.dto.CreateTaskRequest;
import com.taskflow.project_service.model.Project;
import com.taskflow.project_service.model.Task;
import com.taskflow.project_service.security.AuthUtil;
import com.taskflow.project_service.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;
    private final AuthUtil authUtil;

    public ProjectController(
            ProjectService service,
            AuthUtil authUtil) {

        this.service = service;
        this.authUtil = authUtil;
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
            @PathVariable @NonNull UUID projectId,
            HttpServletRequest request) {

        String userId =
                authUtil.getAuthenticatedUser(request);

        return service.getProjectById(
                projectId,
                userId
        );
    }

    @PostMapping("/{projectId}/tasks")
    public Task createTask(
            @PathVariable @NonNull UUID projectId,
            @Valid
            @RequestBody CreateTaskRequest request,
            HttpServletRequest httpRequest) {

        String userId =
                authUtil.getAuthenticatedUser(httpRequest);

        return service.createTask(
                projectId,
                request,
                userId
        );
    }

    @GetMapping("/{projectId}/tasks")
    public List<Task> getTasks(
            @PathVariable @NonNull UUID projectId,
            HttpServletRequest request) {

        String userId =
                authUtil.getAuthenticatedUser(request);

        return service.getTasks(
                projectId,
                userId
        );
    }
}