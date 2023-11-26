package com.satyam.taskmanagement.controller;

import com.satyam.taskmanagement.model.Task;
import com.satyam.taskmanagement.payload.TaskRequest;
import com.satyam.taskmanagement.payload.TaskResponse;
import com.satyam.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long taskId){
        TaskResponse taskResponse = taskService.getTaskById(taskId);
        return new ResponseEntity<>(taskResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest taskRequest){
        Task task = taskService.createTask(taskRequest);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@RequestBody TaskRequest taskRequest, @PathVariable Long taskId){
        return new ResponseEntity<>(taskService.updateTask(taskRequest, taskId), HttpStatus.OK);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Task Deleted Successfully", HttpStatus.OK);
    }

    @PostMapping(value = "/assign/{taskId}/assign/{userId}", produces = "application/json")
    public ResponseEntity<TaskResponse> assignTaskToUser(@PathVariable("taskId") Long taskId, @PathVariable Long userId){
        TaskResponse assignedTask = taskService.assignTaskToUser(taskId, userId);
        return new ResponseEntity<>(assignedTask, HttpStatus.OK);
    }
}
