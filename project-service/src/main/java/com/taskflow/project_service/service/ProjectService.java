package com.taskflow.project_service.service;

import com.taskflow.project_service.client.UserClient;
import com.taskflow.project_service.dto.CreateProjectRequest;
import com.taskflow.project_service.dto.CreateTaskRequest;
import com.taskflow.project_service.model.Project;
import com.taskflow.project_service.model.Task;
import com.taskflow.project_service.permify.PermifyService;
import com.taskflow.project_service.repository.ProjectRepository;
import com.taskflow.project_service.repository.TaskRepository;

import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserClient userClient;
    private final PermifyService permifyService;

    public ProjectService(
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            UserClient userClient,
            PermifyService permifyService) {

        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userClient = userClient;
        this.permifyService = permifyService;
    }

    public Project createProject(
            CreateProjectRequest request) {

        try {
            userClient.getUser(
                    request.getOwnerId()
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Owner user not found"
            );
        }

        Project project = new Project();

        project.setName(
                request.getName()
        );

        project.setOwnerId(
                request.getOwnerId()
        );

        return projectRepository.save(
                project
        );
    }

    public List<Project> getProjects() {

        return projectRepository.findAll();
    }

    public Project getProjectById(
            @NonNull     UUID projectId,
            String currentUserId) {

        Project project =
                projectRepository.findById(
                                projectId
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Project not found"
                                )
                        );

        boolean allowed =
                permifyService.canViewProject(
                        currentUserId,
                        projectId.toString()
                );

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not allowed to view this project"
            );
        }

        return project;
    }

    public Task createTask(
            @NonNull     UUID projectId,
            CreateTaskRequest request,
            String currentUserId) {

        projectRepository.findById(
                        projectId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Project not found"
                        )
                );

        boolean allowed =
                permifyService.canCreateTask(
                        currentUserId,
                        projectId.toString()
                );

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not allowed to create task in this project"
            );
        }

        Task task = new Task();

        task.setProjectId(
                projectId
        );

        task.setTitle(
                request.getTitle()
        );

        return taskRepository.save(
                task
        );
    }

    public List<Task> getTasks(
            @NonNull     UUID projectId,
            String currentUserId) {

        projectRepository.findById(
                        projectId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Project not found"
                        )
                );

        boolean allowed =
                permifyService.canViewProject(
                        currentUserId,
                        projectId.toString()
                );

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not allowed to view tasks"
            );
        }

        return taskRepository.findByProjectId(
                projectId
        );
    }
}