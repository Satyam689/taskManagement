package com.satyam.taskmanagement.service;

import com.satyam.taskmanagement.exception.ResourceNotFoundException;
import com.satyam.taskmanagement.model.Task;
import com.satyam.taskmanagement.model.User;
import com.satyam.taskmanagement.payload.TaskRequest;
import com.satyam.taskmanagement.payload.TaskResponse;
import com.satyam.taskmanagement.repository.TaskRepository;
import com.satyam.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public Task createTask(TaskRequest taskRequest){

        Task task = Task.builder()
                .taskDescription(taskRequest.getDescription())
                .priority(taskRequest.getPriority())
                .taskCategory(taskRequest.getTaskCategory())
                .dueDate(taskRequest.getDueDate())
                .build();

         return taskRepository.save(task);
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    public TaskResponse getTaskById(Long taskId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        Long userId = null;
        if (task.getUser() != null){
            userId = task.getUser().getId();
        }

        TaskResponse taskResponse = TaskResponse.builder()
                .id(task.getId())
                .taskDescription(task.getTaskDescription())
                .priority(task.getPriority())
                .taskCategory(task.getTaskCategory())
                .userId(userId)
                .dueDate(task.getDueDate())
                .build();

        return taskResponse;
    }

    public Task updateTask(TaskRequest taskRequest, Long taskId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        task.setTaskDescription(task.getTaskDescription());
        task.setPriority(taskRequest.getPriority());
        task.setTaskCategory(taskRequest.getTaskCategory());
        task.setDueDate(taskRequest.getDueDate());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        taskRepository.delete(task);
    }

    public TaskResponse assignTaskToUser(Long taskId, Long userId){

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        task.setUser(user);
        task = taskRepository.save(task);

        emailService.sendMessage(user.getEmail(), "About task assignment", String.format("Task with %d is assigned to you", task.getId()));

        TaskResponse taskResponse = TaskResponse.builder()
                .id(task.getId())
                .taskDescription(task.getTaskDescription())
                .taskCategory(task.getTaskCategory())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();

        return taskResponse;
    }

}
