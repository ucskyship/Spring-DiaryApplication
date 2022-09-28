package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.UpdateDiaryRequest;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.DiaryNotFoundException;
import com.ucj.diary.exceptions.EntryAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DiaryServiceImplTest {

    @BeforeEach
    void setUp() {
//        userService.deleteAll();
        diaryService.deleteAll();
    }

    @Autowired
    private iDiaryService diaryService;
    private iUserService userService;

    @Test
    void registerDiary() throws DiaryNotFoundException, DiaryAlreadyExistException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("ucjDiary");
        diaryService.registerUserDiary(request);

        var savedDiary = diaryService.findByDiaryName("ucjDiary");
        assertEquals(savedDiary.getDiaryName(), "ucjDiary");
    }

    @Test
    void addEntryToDiary() throws DiaryAlreadyExistException, DiaryNotFoundException, EntryAlreadyExistException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("ucjDiary");
        diaryService.registerUserDiary(request);

        var savedDiary = diaryService.findByDiaryName("ucjDiary");

        RegisterEntryRequest request1 = new RegisterEntryRequest();
        request1.setTittle("tittle");
        request1.setBody("now now");
        request1.setDiaryName("ucjDiary");
        diaryService.addEntryToDiary(request1);

        savedDiary = diaryService.findByDiaryName("ucjDiary");
        assertEquals("tittle", savedDiary.getEntries().get(0).getTitle());
    }

    @Test
    void findDiaryById() throws DiaryAlreadyExistException, DiaryNotFoundException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("ucjDiary");
        var response = diaryService.registerUserDiary(request);

        var savedDiary = diaryService.findDiaryById(response.getId());
        assertEquals(savedDiary.getDiaryName(), "ucjDiary");
    }

    @Test
    void findByDiaryName() throws DiaryAlreadyExistException, DiaryNotFoundException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("ucjDiary");
        diaryService.registerUserDiary(request);

        var foundDiary = diaryService.findByDiaryName("ucjDiary");
        assertEquals(foundDiary.getDiaryName(), "ucjDiary");
    }

    @Test
    void updateDiary() throws DiaryAlreadyExistException, DiaryNotFoundException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("myDiary");
        diaryService.registerUserDiary(request);

        var foundDiary = diaryService.findByDiaryName("myDiary");
        assertEquals(foundDiary.getDiaryName(), "myDiary");

        UpdateDiaryRequest requestUpdate = new UpdateDiaryRequest();
        requestUpdate.setDiaryName("myDiary");
        requestUpdate.setNewDiaryName("newName");
        var updated = diaryService.updateDiary(requestUpdate);
        assertEquals(updated.getDiaryName(), "newName");
    }

    @Test
    void deleteDiary() throws DiaryAlreadyExistException, DiaryNotFoundException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("myDiary");

        var diaryToDelete = diaryService.registerUserDiary(request);
        var foundDiary = diaryService.findByDiaryName("myDiary");
        assertEquals(foundDiary.getDiaryName(), "myDiary");

        diaryService.deleteDiary(diaryToDelete.getId());
        assertEquals(diaryService.count(), 0);
    }

    @Test
    void deleteAllDiary() throws DiaryAlreadyExistException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("myDiary");
        diaryService.registerUserDiary(request);

        RegisterDiaryRequest request2 = new RegisterDiaryRequest();
        request2.setDiaryName("myDiary2");
        diaryService.registerUserDiary(request2);

        assertEquals(diaryService.count(), 2);
        diaryService.deleteAll();
        assertEquals(diaryService.count(), 0);
    }

    @Test
    void throwsDiaryAlreadyExistException() throws DiaryAlreadyExistException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("myDiary");
        diaryService.registerUserDiary(request);

        RegisterDiaryRequest request2 = new RegisterDiaryRequest();
        request2.setDiaryName("myDiary");

        assertThrows(DiaryAlreadyExistException.class, ()-> diaryService.registerUserDiary(request2));
    }

    @Test
    void throwsDiaryNotFoundException() throws DiaryAlreadyExistException {
        RegisterDiaryRequest request = new RegisterDiaryRequest();
        request.setDiaryName("myDiary");
        diaryService.registerUserDiary(request);
         assertThrows(DiaryNotFoundException.class, ()-> diaryService.findByDiaryName("unknown"));
    }
}
