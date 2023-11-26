package com.satyam.taskmanagement.payload;

import com.satyam.taskmanagement.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String taskDescription;
    private Priority priority;
    private String taskCategory;
    private Long userId;
    private Date dueDate;
}
