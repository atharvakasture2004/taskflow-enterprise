package com.taskflow.user_service.service;

import com.taskflow.user_service.dto.CreateUserRequest;
import com.taskflow.user_service.model.UserProfile;
import com.taskflow.user_service.repository.UserProfileRepository;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserProfileRepository repository;

    public UserService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public UserProfile create(CreateUserRequest request) {

        UserProfile user = new UserProfile();

        user.setEmail(request.getEmail());
        user.setName(request.getName());

        return repository.save(user);
    }

    public List<UserProfile> getAll() {
        return repository.findAll();
    }

    public UserProfile getById(@NonNull UUID id) {
        return repository.findById(id)
                .orElseThrow();
    }
}