package com.maxit.maxit221.exception;


import com.maxit.maxit221.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(value ={Exception.class})
    public ErrorDto handlerException(Exception exception){
        return ErrorDto.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = {MaxItNotFoundException.class})
    public ErrorDto handlerException(MaxItNotFoundException exception){
        return ErrorDto.builder()
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {MaxItCompleteException.class})
    public ErrorDto handlerException(MaxItCompleteException exception){
        return ErrorDto.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(exception.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Map<String,String> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((error)->{
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }
}
