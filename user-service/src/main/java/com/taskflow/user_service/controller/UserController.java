package com.taskflow.user_service.controller;

import com.taskflow.user_service.dto.CreateUserRequest;
import com.taskflow.user_service.model.UserProfile;
import com.taskflow.user_service.service.UserService;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserProfile create(
            @RequestBody CreateUserRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<UserProfile> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserProfile getById(
            @PathVariable @NonNull UUID id) {

        return service.getById(id);
    }
}