package com.ucj.diary.controllers;

import com.ucj.diary.data.dtos.requests.*;
import com.ucj.diary.exceptions.*;
import com.ucj.diary.services.DiaryServiceImpl;
import com.ucj.diary.services.EntryServiceImpl;
import com.ucj.diary.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class Controller {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DiaryServiceImpl diaryService;
    @Autowired
    private EntryServiceImpl entryService;


    @PostMapping("/reg_user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws UserAlreadyExistException {
        try {
            return new ResponseEntity<>(userService.registerUser(registerUserRequest), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            throw new UserAlreadyExistException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reg_diary")
    public ResponseEntity<?> registerDiary(@RequestBody RegisterDiaryRequest registerDiaryRequest) {
        try {
            return new ResponseEntity<>(userService.addDiary(registerDiaryRequest), HttpStatus.CREATED);
        } catch (UserNotFoundException | DiaryAlreadyExistException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/reg_entry")
    public ResponseEntity<?> registerUserDiaryEntry(@RequestBody RegisterEntryRequest registerEntryRequest) {
        try {
            return new ResponseEntity<>(diaryService.addEntryToDiary(registerEntryRequest), HttpStatus.CREATED);
        } catch (EntryAlreadyExistException | DiaryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
        } catch (UserNotFoundException er) {
            throw new RuntimeException(er);
        }
    }

    @GetMapping("/diary_by/{id}")
    public ResponseEntity<?> findUserDiaryById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(diaryService.findDiaryById(id), HttpStatus.OK);
        } catch (DiaryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/find_entry_by/{id}")
    public ResponseEntity<?> findUserEntryById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(entryService.findEntryId(id), HttpStatus.OK);
        } catch (EntryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/find_user_by_name")
    public ResponseEntity<?> findUserByUserName(@RequestParam String userName) {
        try {
            return new ResponseEntity<>(userService.findUserByUserName(userName), HttpStatus.OK);
        } catch (UserNotFoundException er) {
            throw new RuntimeException(er);
        }
    }

    @GetMapping("/find_diary_by_name")
    public ResponseEntity<?> findDiaryByDiaryName(@RequestParam String diaryName) {
        try {
            return new ResponseEntity<>(diaryService.findByDiaryName(diaryName), HttpStatus.OK);
        } catch (DiaryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/find_entry_by_tittle")
    public ResponseEntity<?> findEntryByTittle(@RequestParam String tittle) {
        try {
            return new ResponseEntity<>(entryService.findEntryByTittle(tittle), HttpStatus.OK);
        } catch (EntryNotFoundException er) {
            throw new RuntimeException(er);
        }
    }

    @PutMapping("update_user_details")
    public ResponseEntity<?> updateUserDetails(@RequestBody UpdateUserRequest updateUserRequest) {
        try {
            return new ResponseEntity<>(userService.updateUser(updateUserRequest), HttpStatus.OK);
        } catch (UserNotFoundException er) {
            throw new RuntimeException(er);
        }
    }

    @PutMapping("update_diary_info")
    public ResponseEntity<?> updateUserDiaryInfo(@RequestBody UpdateDiaryRequest updateDiaryRequest) {
        return new ResponseEntity<>(diaryService.updateDiary(updateDiaryRequest), HttpStatus.OK);
    }

    @PutMapping("update_entry_info")
    public ResponseEntity<?> updateUserDiaryEntryInfo(@RequestBody UpdateEntryRequest updateEntryRequest) throws EntryNotFoundException {
        return new ResponseEntity<>(entryService.updateEntry(updateEntryRequest), HttpStatus.OK);
    }

    @DeleteMapping("/del_user_by_{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(userService.deleteUer(id), HttpStatus.OK);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @DeleteMapping("/del_diary_by_{id}")
    public ResponseEntity<?> deleteDiaryById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(diaryService.deleteDiary(id), HttpStatus.OK);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @DeleteMapping("/del_entry_by_{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(entryService.deleteEntry(id), HttpStatus.OK);
        } catch (Exception error) {
            throw new RuntimeException(error);
        }
    }

    @DeleteMapping("clearAllUsers")
    public ResponseEntity<?> deleteAllUsers() {
        try {
            return new ResponseEntity<>(userService.deleteAll(), HttpStatus.GONE);
        } catch (Exception er) {
            throw new RuntimeException(er);
        }
    }

    @DeleteMapping("clearAllDiary")
    public ResponseEntity<?> deleteAllDiary() {
        try {
            return new ResponseEntity<>(diaryService.deleteAll(), HttpStatus.GONE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("clearAllEntry")
    public ResponseEntity<?> deleteAllEntry() {
        try {
            return new ResponseEntity<>(entryService.deleteAll(), HttpStatus.GONE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
