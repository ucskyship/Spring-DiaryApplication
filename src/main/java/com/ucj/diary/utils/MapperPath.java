package com.ucj.diary.utils;

import com.ucj.diary.data.dtos.requests.*;
import com.ucj.diary.data.models.Diary;
import com.ucj.diary.data.models.Entry;
import com.ucj.diary.data.models.User;

public class MapperPath {
    public static void mapUser(RegisterUserRequest request, User user) {
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getUserName() != null) {
            user.setUserName(request.getUserName());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
    }

    public static void mapUpdate(UpdateUserRequest request, User user) {
        if (request.getFirstName() == null) {
            user.setFirstName(user.getFirstName());
        }else user.setFirstName(request.getFirstName());

        if (request.getLastName() == null) {
            user.setLastName(user.getLastName());
        }else user.setLastName(request.getLastName());

        if (request.getUserName() == null) {
            user.setUserName(user.getUserName());
        }else user.setUserName(request.getUserName());

        if (request.getPassword() == null) {
            user.setPassword(user.getPassword());
        }else user.setPassword(request.getPassword());
    }

    public static void mapDiary(RegisterDiaryRequest request, Diary diary) {
        if (request.getDiaryName() != null) diary.setDiaryName(request.getDiaryName());
    }

    public static void mapDiaryUpdate(UpdateDiaryRequest request, Diary diary) {
        if (request.getNewDiaryName() != null) diary.setDiaryName(request.getNewDiaryName());
    }

    public static void mapEntry(RegisterEntryRequest request, Entry entry) {
        if (request.getTittle() != null) {
            entry.setTitle(request.getTittle());
        }
        if (request.getBody() != null) {
            entry.setBody(request.getBody());
        }
    }

    public static void mapEntry(UpdateEntryRequest request, Entry entry) {
        if (request.getOldTittle() != null) {
            entry.setTitle(request.getTittle());
        }
        if (request.getBody() != null) {
            entry.setBody(request.getBody());
        }
    }
}
