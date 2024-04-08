package com.edu.tiktalk_backend.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UniqueException extends RuntimeException {
    public UniqueException(String message) {
        super(message);
    }
}
