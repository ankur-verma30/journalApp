package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

//controller ---> service ---> repository ---> database ---> service ---> controller ---> view/frontend
@Service
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {
            User user = userEntryService.findByUserName(userName); // we will get user by username
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry); // save journal to the db
            user.getJournalEntries().add(savedEntry); // add that journal entry in the list of journal_entries of that user as well, but it happened in In-memory not in DB
            userEntryService.saveUser(user); // so save the user again it will update the user
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry " + e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }


    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean isRemoved = false;
        try {
            User user = userEntryService.findByUserName(userName);
            isRemoved = user.getJournalEntries().removeIf(journal -> journal.getId().equals(id));
            if (isRemoved) {
                userEntryService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Error While Deleting the Journal Entry", e);
        }
        return isRemoved;
    }

    public List<JournalEntry> findByUserName(String userName) {
        return null;
    }
}
