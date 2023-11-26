package com.satyam.taskmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserExistException extends RuntimeException{

    private final String username;

    public UserExistException(String username){
        super(String.format("user with %s already exist", username));
        this.username = username;
    }
}
