package com.example.api;

import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/users/")
public interface UserAPI {

    @PostMapping
    UUID create(@RequestBody UserRequest userRequest);

    @GetMapping
    List<UserResponse> getAll();

}
