package com.satyam.taskmanagement.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.satyam.taskmanagement.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    @NotBlank(message = "Name should not be empty")
    @Size(max = 200, min = 10)
    private String description;
    private Priority priority;
    @NotBlank(message = "Category should not be empty")
    private String taskCategory;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date dueDate;
}
