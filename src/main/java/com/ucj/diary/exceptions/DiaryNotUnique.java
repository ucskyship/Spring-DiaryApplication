package com.ucj.diary.exceptions;

public class DiaryNotUnique extends RuntimeException {
    public DiaryNotUnique(String message) {
        super(message);
    }
}
