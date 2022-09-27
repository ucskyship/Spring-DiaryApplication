package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.data.dtos.responses.RegisterEntryResponse;
import com.ucj.diary.data.models.Diary;
import com.ucj.diary.data.models.Entry;
import com.ucj.diary.data.repositories.DiaryRepository;
import com.ucj.diary.data.dtos.requests.RegisterDiaryRequest;
import com.ucj.diary.data.dtos.requests.UpdateDiaryRequest;
import com.ucj.diary.exceptions.DiaryAlreadyExistException;
import com.ucj.diary.exceptions.DiaryNotFoundException;
import com.ucj.diary.exceptions.EntryAlreadyExistException;
import com.ucj.diary.exceptions.DiaryNotUnique;
import com.ucj.diary.utils.MapperPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryServiceImpl implements iDiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EntryServiceImpl entryService;

    private List<Entry> entries = new ArrayList<>();

    @Override
    public Diary registerUserDiary(RegisterDiaryRequest request) throws DiaryAlreadyExistException {
        for (var foundDiary : diaryRepository.findAll()) {
            if (foundDiary.getDiaryName().equals(request.getDiaryName())) {
                throw new DiaryAlreadyExistException("diary already exist");
            }
            if (foundDiary.getDiaryName() != null) {
                throw new DiaryNotUnique("cannot create diary user not unique");
            }
        }
        Diary diary = new Diary();
        MapperPath.mapDiary(request, diary);
        return diaryRepository.save(diary);
    }

    @Override
    public RegisterEntryResponse addEntryToDiary(RegisterEntryRequest request) throws EntryAlreadyExistException, DiaryNotFoundException {
        var savedEntry = entryService.registerEntry(request);
        entries.add(savedEntry);
        var foundDiary = findByDiaryName(request.getDiaryName());
        foundDiary.setEntries(entries);
        diaryRepository.save(foundDiary);

        RegisterEntryResponse response = new RegisterEntryResponse();
        response.setMessage("entry added");
        return response;
    }

    @Override
    public Diary findDiaryById(String id) throws DiaryNotFoundException {
        Optional<Diary> foundUser = diaryRepository.findById(id);
        if (foundUser.isPresent()) {
            return foundUser.get();
        }
        throw new DiaryNotFoundException("diary not found");
    }

    @Override
    public Diary findByDiaryName(String diaryName) throws DiaryNotFoundException {
        var foundDiary = diaryRepository.findByDiaryName(diaryName);
        if (foundDiary == null) {
            throw new DiaryNotFoundException("diary not found");
        }
        return foundDiary;
    }

    @Override
    public Diary updateDiary(UpdateDiaryRequest request) {
        var update = diaryRepository.findByDiaryName(request.getDiaryName());
        MapperPath.mapDiaryUpdate(request, update);
        diaryRepository.save(update);
        return update;
    }

    @Override
    public String deleteDiary(String id) {
        diaryRepository.deleteById(id);
        return "deleted";
    }

    @Override
    public String deleteAll() {
        diaryRepository.deleteAll();
        return "Empty";
    }

    @Override
    public long count() {
        return diaryRepository.count();
    }
}
