package com.unborn.blogger.exceptions;

import com.unborn.blogger.datatransferobject.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        Boolean status = false;
        ApiResponse apiResponse = new ApiResponse(message,status);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String ,String>> handleMethodArgsNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> resp = new HashMap<String,String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError)(error)).getField();
            String errorMessage = error.getDefaultMessage();
            resp.put(field, errorMessage);
        });
        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
        
    }
}
