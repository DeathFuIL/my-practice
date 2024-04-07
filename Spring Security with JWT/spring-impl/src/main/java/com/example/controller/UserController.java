package com.example.controller;

import com.example.api.UserAPI;
import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public UUID create(UserRequest userRequest) {
        System.out.println(userRequest);
        return userService.create(userRequest);
    }

    @Override
    public List<UserResponse> getAll() {
        return userService.getAll();
    }
}
