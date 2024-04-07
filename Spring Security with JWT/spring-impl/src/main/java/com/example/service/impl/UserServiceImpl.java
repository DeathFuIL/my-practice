package com.example.service.impl;

import com.example.dto.request.UserRequest;
import com.example.dto.response.UserResponse;
import com.example.entity.UserEntity;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.security.details.UserDetailsImpl;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getById(UUID id) {
        return null;
    }

    @Override
    public UUID create(UserRequest userRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity userEntity = userMapper.toEntity(userRequest);
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        return userRepository.saveAndFlush(userEntity).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user");
        return new UserDetailsImpl(
                userRepository.findUserEntityByEmail(username)
                        .orElse(new UserEntity()) //user with nullable fields
        );
    }


}
