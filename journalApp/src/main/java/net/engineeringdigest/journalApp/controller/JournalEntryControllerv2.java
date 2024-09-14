package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


//    @GetMapping("{userName}")
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
       User user = userService.findByUserName(userName);
//        List<JournalEntry> all = journalEntryService.getAll();
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("{userName}")
@PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {

        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(entry, userName);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable String myId) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("id/{userName}/{myId}")
    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String myId) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

//    @PutMapping("id/{userName}/{myId}")
@PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String myId, @RequestBody JournalEntry entry) {
    Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();

    User user = userService.findByUserName(userName);
    List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

    if(!collect.isEmpty()){
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myId);
        if(journalEntry.isPresent()){
            JournalEntry old = journalEntry.get();
            old.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
            old.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
    }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
