package com.satyam.taskmanagement.controller;

import com.satyam.taskmanagement.model.Task;
import com.satyam.taskmanagement.model.User;
import com.satyam.taskmanagement.payload.UserRequest;
import com.satyam.taskmanagement.payload.UserResponse;
import com.satyam.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("createUser")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        User user = userService.createUser(userRequest);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tasksId(user.getTasks().stream().map(Task::getId).collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponse>> getUsers(){
        List<UserResponse> userResponses = userService.getUser();
        return new  ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId){
        UserResponse userResponse = userService.getUserById(userId);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
