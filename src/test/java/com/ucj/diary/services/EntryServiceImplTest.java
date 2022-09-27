package com.ucj.diary.services;

import com.ucj.diary.data.dtos.requests.RegisterEntryRequest;
import com.ucj.diary.data.dtos.requests.UpdateEntryRequest;
import com.ucj.diary.exceptions.EntryAlreadyExistException;
import com.ucj.diary.exceptions.EntryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryServiceImplTest {

    @BeforeEach
    void setUp() {
        entryService.deleteAll();
    }

    @Autowired
    private iEntryService entryService;

    @Test
    void registerEntry() throws EntryAlreadyExistException, EntryNotFoundException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("this is my first entry body for testing");
        entryService.registerEntry(request);

        var savedEntry = entryService.findEntryByTittle("entry1");
        assertEquals(savedEntry.getTitle(), "entry1");
    }

    @Test
    void findEntryId() throws EntryAlreadyExistException, EntryNotFoundException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        var found = entryService.findEntryByTittle("entry1");
        var foundEntry = entryService.findEntryId(found.getId());
        assertEquals(foundEntry.getTitle(), "entry1");
        assertEquals(foundEntry.getBody(), "how are you doing over there");
    }

    @Test
    void findEntryByTittle() throws EntryAlreadyExistException, EntryNotFoundException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        var foundEntry = entryService.findEntryByTittle("entry1");
        assertEquals(foundEntry.getTitle(), "entry1");
        assertEquals(foundEntry.getBody(), "how are you doing over there");
    }

    @Test
    void updateEntry() throws EntryAlreadyExistException, EntryNotFoundException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        UpdateEntryRequest requestUpdate = new UpdateEntryRequest();
        requestUpdate.setTittle("entry1");
        requestUpdate.setBody("when are you coming over there");
        var entry = entryService.updateEntry(requestUpdate);
        var foundEntry = entryService.findEntryId(entry.getId());

        assertEquals(foundEntry.getBody(), "when are you coming over there");
    }

    @Test
    void deleteEntry() throws EntryAlreadyExistException, EntryNotFoundException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        var saved = entryService.findEntryByTittle("entry1");
        var deletedEntry = entryService.deleteEntry(saved.getId());
        assertEquals(entryService.count(), 0);
    }

    @Test
    void deleteAllEntry() throws EntryAlreadyExistException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        RegisterEntryRequest request2 = new RegisterEntryRequest();
        request2.setTittle("entry2");
        request2.setBody("how are you doing over there");
        entryService.registerEntry(request2);

        entryService.deleteAll();
        assertEquals(entryService.count(), 0);
    }

    @Test
    void throwsEntryNotFoundException() throws EntryAlreadyExistException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);
        assertThrows(EntryNotFoundException.class, ()-> entryService.findEntryId("unknown id"));
        assertThrows(EntryNotFoundException.class, ()-> entryService.findEntryByTittle("unknown"));
    }

    @Test
    void throwsEntryAlreadyExistException() throws EntryAlreadyExistException {
        RegisterEntryRequest request = new RegisterEntryRequest();
        request.setTittle("entry1");
        request.setBody("how are you doing over there");
        entryService.registerEntry(request);

        RegisterEntryRequest request1 = new RegisterEntryRequest();
        request1.setTittle("entry1");
        request1.setBody("how are you doing over there");
        assertThrows(EntryAlreadyExistException.class, ()-> entryService.registerEntry(request1));
    }
}
