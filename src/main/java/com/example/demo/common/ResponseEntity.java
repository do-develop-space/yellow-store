package com.example.demo.common;

import lombok.Getter;

@Getter
public class ResponseEntity<T> {
    private final int status;
    private final T data;
    private final long count;

    public ResponseEntity(int value, T all, long count) {
        this.status = value;
        this.data = all;
        this.count = count;
    }
}
