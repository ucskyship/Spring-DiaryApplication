package com.ucj.diary.services;

import com.ucj.diary.data.models.User;
import com.ucj.diary.data.repositories.UserRepository;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.RegisterUserRequest;
import com.ucj.diary.data.dtos.requests.UpdateUserRequest;
import com.ucj.diary.data.dtos.responses.RegisterDiaryResponse;
import com.ucj.diary.data.dtos.responses.RegisterUserResponse;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.UserAlreadyExistException;
import com.ucj.diary.exceptions.UserNotFoundException;
import com.ucj.diary.utils.MapperPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements iUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiaryServiceImpl diaryService;

    @Override
    public RegisterUserResponse registerUser(RegisterUserRequest request) throws UserAlreadyExistException {
        for (User foundUser : userRepository.findAll()) {
            if (foundUser.getUserName().equals(request.getUserName())) {
                throw new UserAlreadyExistException("user already exist", HttpStatus.BAD_REQUEST);
            }
        }
        User user = new User();
        MapperPath.mapUser(request, user);
        var savedUser = userRepository.save(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("successfully registered");
        return response;
    }

    @Override
    public RegisterDiaryResponse addDiary(RegisterDiaryRequest request) throws UserNotFoundException, DiaryAlreadyExistException {
        var diary = diaryService.registerUserDiary(request);
        var user = findUserByUserName(request.getUserName());

        user.setDiary(diary);
//        if (diary.getDiaryName() != null) {
//                throw new DiaryNotUnique("cannot create diary user not unique");
//            }
        userRepository.save(user);

        RegisterDiaryResponse response = new RegisterDiaryResponse();
        response.setMessage("registered");
        return response;
    }

    @Override
    public User findUserById(String userId) throws UserNotFoundException {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            return foundUser.get();
        }
        throw new UserNotFoundException("user not found");
    }

    @Override
    public User findUserByUserName(String userName) throws UserNotFoundException {
        var foundUser = userRepository.findByUserName(userName);
        if (foundUser == null) {
            throw new UserNotFoundException("user not found");
        }
        return foundUser;
    }

    @Override
    public User updateUser(UpdateUserRequest request) throws UserNotFoundException {
        var foundUser = findUserById(request.getId());
        MapperPath.mapUpdate(request, foundUser);
        return userRepository.save(foundUser);
    }

    @Override
    public String deleteUer(String id) {
        userRepository.deleteById(id);
        return "deleted";
    }

    @Override
    public String deleteAll() {
        userRepository.deleteAll();
        return "Empty";
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
