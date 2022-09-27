package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.UpdateEntryRequest;
import com.ucj.diary.data.models.Entry;
import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.data.dtos.responses.RegisterEntryResponse;
import com.ucj.diary.exceptions.EntryAlreadyExistException;
import com.ucj.diary.exceptions.EntryNotFoundException;

public interface iEntryService {
    Entry registerEntry(RegisterEntryRequest request) throws EntryAlreadyExistException;
    Entry findEntryId(String id) throws EntryNotFoundException;
    Entry findEntryByTittle(String tittle) throws EntryNotFoundException;
    Entry updateEntry(UpdateEntryRequest request) throws EntryNotFoundException;
    String deleteEntry(String id);
    String deleteAll();
    long count();
}
