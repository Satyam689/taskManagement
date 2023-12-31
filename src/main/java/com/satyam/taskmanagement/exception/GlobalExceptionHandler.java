package com.satyam.taskmanagement.exception;

import com.satyam.taskmanagement.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorDetails> userExistException(UserExistException exception, WebRequest webRequest){

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException exception, WebRequest webRequest){

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timeStamp(new Date())
                .message(exception.getMessage())
                .details(webRequest.getDescription(false))
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
