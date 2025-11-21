package com.example.demo.aop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SellerNotFoundException extends IllegalAccessException{

    public SellerNotFoundException(UUID id) {
        super("Not found with id: " + id);
    }
}
