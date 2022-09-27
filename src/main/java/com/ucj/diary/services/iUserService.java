package com.ucj.diary.services;

import com.ucj.diary.data.models.User;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.RegisterUserRequest;
import com.ucj.diary.data.dtos.requests.UpdateUserRequest;
import com.ucj.diary.data.dtos.responses.RegisterDiaryResponse;
import com.ucj.diary.data.dtos.responses.RegisterUserResponse;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.UserAlreadyExistException;
import com.ucj.diary.exceptions.UserNotFoundException;

public interface iUserService {
    RegisterUserResponse registerUser(RegisterUserRequest request) throws UserAlreadyExistException;
    User findUserById(String userId) throws UserNotFoundException;
    User findUserByUserName(String userName) throws UserNotFoundException;
    User updateUser(UpdateUserRequest request) throws UserNotFoundException;
    String deleteUer(String id) ;
    String deleteAll();
    Long count();
    RegisterDiaryResponse addDiary(RegisterDiaryRequest request) throws UserNotFoundException, DiaryAlreadyExistException;
}
