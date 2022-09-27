package com.ucj.diary.data.repositories;

import com.ucj.diary.data.models.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, String> {
}
