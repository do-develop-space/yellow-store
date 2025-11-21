package com.example.homework.aop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends IllegalAccessException{

    public NotFoundException(UUID id) {
        super("Not found with id: " + id);
    }
}
