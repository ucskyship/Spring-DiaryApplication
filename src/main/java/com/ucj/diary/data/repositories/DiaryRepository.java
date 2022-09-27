package com.ucj.diary.data.repositories;

import com.ucj.diary.data.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiaryRepository extends MongoRepository<Diary, String> {
    Diary findByDiaryName(String diaryName);
}
