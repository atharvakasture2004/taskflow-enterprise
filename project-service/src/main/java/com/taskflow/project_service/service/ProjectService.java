package com.taskflow.project_service.service;

import com.taskflow.project_service.client.UserClient;
import com.taskflow.project_service.dto.CreateProjectRequest;
import com.taskflow.project_service.dto.CreateTaskRequest;
import com.taskflow.project_service.model.Project;
import com.taskflow.project_service.model.Task;
import com.taskflow.project_service.repository.ProjectRepository;
import com.taskflow.project_service.repository.TaskRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserClient userClient;

    public ProjectService(
            ProjectRepository projectRepository,
            TaskRepository taskRepository,
            UserClient userClient) {

        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userClient = userClient;
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
            @NonNull UUID projectId) {

        return projectRepository.findById(
                        projectId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Project not found"
                        )
                );
    }

    public Task createTask(
            @NonNull UUID projectId,
            CreateTaskRequest request) {

        projectRepository.findById(
                        projectId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Project not found"
                        )
                );

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
            @NonNull UUID projectId) {

        projectRepository.findById(
                        projectId
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Project not found"
                        )
                );

        return taskRepository.findByProjectId(
                projectId
        );
    }
}