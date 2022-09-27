package com.ucj.diary.services;

import com.ucj.diary.data.models.User;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.RegisterUserRequest;
import com.ucj.diary.data.dtos.requests.UpdateUserRequest;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.UserAlreadyExistException;
import com.ucj.diary.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @BeforeEach
    void setUp() {
        userService.deleteAll();
    }

    @Autowired
    private iUserService userService;

    @Autowired
    private iDiaryService diaryService;

    @Test
    void registerUser() throws UserAlreadyExistException, UserNotFoundException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        var registeredUser = userService.findUserByUserName("ucj");
        assertEquals(registeredUser.getLastName(), "joshua");
    }

    @Test
    void addDiaryToUser() throws UserAlreadyExistException, UserNotFoundException, DiaryAlreadyExistException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        User savedUser = userService.findUserByUserName("ucj");

        RegisterDiaryRequest request1 = new RegisterDiaryRequest();
        request1.setDiaryName("userDiary");
        request1.setUserName(savedUser.getUserName());

        userService.addDiary(request1);
        savedUser = userService.findUserByUserName("ucj");
        assertEquals("userDiary", savedUser.getDiary().getDiaryName());
    }

    @Test
    void getUserById() throws UserAlreadyExistException, UserNotFoundException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        User savedUser = userService.findUserByUserName("ucj");

        var registeredUser = userService.findUserById(savedUser.getId());
        assertEquals("ucj", registeredUser.getUserName());
    }

    @Test
    void getUserByUserName() throws UserAlreadyExistException, UserNotFoundException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        var foundUser = userService.findUserByUserName("ucj");
        assertEquals("ucj", foundUser.getUserName());
    }

    @Test
    void updateUser() throws UserAlreadyExistException, UserNotFoundException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        User savedUser = userService.findUserByUserName("ucj");

        RegisterUserRequest request2 = new RegisterUserRequest();
        request2.setFirstName("uzochukwu");
        request2.setLastName("joshua");
        request2.setUserName("me");
        request2.setPassword("password");
        userService.registerUser(request2);

        var foundUser = userService.findUserById(savedUser.getId());
        assertEquals("uzochukwu", foundUser.getFirstName());

        UpdateUserRequest requestUpdate = new UpdateUserRequest();
        requestUpdate.setId(foundUser.getId());
        requestUpdate.setFirstName("newFirstName");
        requestUpdate.setUserName("ucj");

        var newUser = userService.updateUser(requestUpdate);
        assertEquals(newUser.getFirstName(), "newFirstName");
        assertEquals(newUser.getUserName(), "ucj");
        assertEquals(newUser.getLastName(), "joshua");
        assertEquals(2, userService.count());
    }

    @Test
    void deleteUer() throws UserAlreadyExistException, UserNotFoundException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("uzochukwu");
        request.setLastName("joshua");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        var foundUser = userService.findUserByUserName("ucj");
        assertEquals(foundUser.getFirstName(), "uzochukwu");

        userService.deleteUer(foundUser.getId());
        assertEquals(0, userService.count());
    }

    @Test
    void testThatThrowsUserAlreadyExistException() throws UserAlreadyExistException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        RegisterUserRequest request2 = new RegisterUserRequest();
        request2.setFirstName("firstName");
        request2.setLastName("lastName");
        request2.setUserName("ucj");
        request2.setPassword("password");

        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(request2));
        assertEquals(1, userService.count());
    }

    @Test
    void throwsUserNotFound() throws UserAlreadyExistException {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setUserName("ucj");
        request.setPassword("password");
        userService.registerUser(request);

        assertThrows(UserNotFoundException.class, ()-> userService.findUserByUserName("unknown"));
        assertThrows(UserNotFoundException.class, ()-> userService.findUserById("unknown id"));
    }
}
