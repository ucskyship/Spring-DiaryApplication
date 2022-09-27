package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.data.dtos.responses.RegisterEntryResponse;
import com.ucj.diary.data.models.Diary;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.UpdateDiaryRequest;
import com.ucj.diary.data.models.Entry;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.DiaryNotFoundException;
import com.ucj.diary.exceptions.EntryAlreadyExistException;

public interface iDiaryService {
    Diary registerUserDiary(RegisterDiaryRequest request) throws DiaryAlreadyExistException;
    RegisterEntryResponse addEntryToDiary(RegisterEntryRequest request) throws EntryAlreadyExistException, DiaryNotFoundException;
    Diary findDiaryById(String id) throws DiaryNotFoundException;
    Diary findByDiaryName(String diaryName) throws DiaryNotFoundException;
    Diary updateDiary(UpdateDiaryRequest request);
    String deleteDiary(String id);
    String deleteAll();
    long count();
}
