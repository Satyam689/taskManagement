package com.satyam.taskmanagement.service;

import com.satyam.taskmanagement.exception.ResourceNotFoundException;
import com.satyam.taskmanagement.exception.UserExistException;
import com.satyam.taskmanagement.model.Task;
import com.satyam.taskmanagement.model.User;
import com.satyam.taskmanagement.payload.UserRequest;
import com.satyam.taskmanagement.payload.UserResponse;
import com.satyam.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserRequest userRequest){
        boolean exist = userRepository.existsByUsername(userRequest.getUsername());
        if(exist){
            throw  new UserExistException(userRequest.getUsername());
        }
        User user = User.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .build();

        return  userRepository.save(user);
    }

    public List<UserResponse> getUser(){
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream().map((user) ->
                UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .tasksId(user.getTasks().stream().map(Task::getId).collect(Collectors.toList()))
                        .build()
                ).collect(Collectors.toList());
        return userResponses;
    }

    public UserResponse getUserByUserName(String username){
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw  new ResourceNotFoundException("User", "username", 0);
        }
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tasksId(user.getTasks().stream().map(Task::getId).collect(Collectors.toList()))
                .build();
        return userResponse;
    }

    public UserResponse getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tasksId(user.getTasks().stream().map(Task::getId).collect(Collectors.toList()))
                .build();
        return  userResponse;
    }
}
