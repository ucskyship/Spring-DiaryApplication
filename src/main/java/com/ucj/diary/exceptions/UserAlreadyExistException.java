package com.ucj.diary.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String message, HttpStatus badRequest) {
        super(message);
    }
}
