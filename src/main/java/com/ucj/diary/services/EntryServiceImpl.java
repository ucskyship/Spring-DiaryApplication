package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.UpdateEntryRequest;
import com.ucj.diary.data.models.Entry;
import com.ucj.diary.data.repositories.EntryRepository;
import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.exceptions.EntryAlreadyExistException;
import com.ucj.diary.exceptions.EntryNotFoundException;
import com.ucj.diary.utils.MapperPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EntryServiceImpl implements iEntryService{
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry registerEntry(RegisterEntryRequest request) throws EntryAlreadyExistException {
        for (Entry foundEntry : entryRepository.findAll()){
            if (request.getTittle().equals(foundEntry.getTitle())){
                throw new EntryAlreadyExistException("entry already exist");
            }
        }
        Entry entry = new Entry();
        MapperPath.mapEntry(request, entry);
        entry.setDateCreated(LocalDateTime.now());
        return entryRepository.save(entry);
    }

    @Override
    public Entry findEntryId(String id) throws EntryNotFoundException {
        var foundEntry = entryRepository.findById(id);
        if (foundEntry.isPresent()){
            return foundEntry.get();
        }
        throw new EntryNotFoundException("entry does not exist");
    }

    @Override
    public Entry findEntryByTittle(String tittle) throws EntryNotFoundException {
        for (Entry foundEntry : entryRepository.findAll()){
            if (foundEntry.getTitle().equals(tittle)){
                return foundEntry;
            }
        }
        throw new EntryNotFoundException("no entry found");
    }

    @Override
    public Entry updateEntry(UpdateEntryRequest request) throws EntryNotFoundException {
        var foundEntry = findEntryByTittle(request.getTittle());
        if (foundEntry != null){
            MapperPath.mapEntry(request, foundEntry);
            foundEntry.setDateCreated(LocalDateTime.now());
            return entryRepository.save(foundEntry);
        }
        throw new EntryNotFoundException("entry not found");
    }

    @Override
    public String deleteEntry(String id) {
        entryRepository.deleteById(id);
        return "deleted";
    }

    @Override
    public String deleteAll() {
        entryRepository.deleteAll();
        return "Empty";
    }

    @Override
    public long count() {
        return entryRepository.count();
    }
}
