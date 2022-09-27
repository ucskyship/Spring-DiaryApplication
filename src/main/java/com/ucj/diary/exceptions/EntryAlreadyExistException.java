package com.ucj.diary.exceptions;

public class EntryAlreadyExistException extends Exception {
    public EntryAlreadyExistException(String message) {
        super(message);
    }
}
