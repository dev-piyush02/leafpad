package com.notes.theidlenotes.controller;

import com.notes.theidlenotes.entity.Notes;
import com.notes.theidlenotes.service.NotesService;
import com.notes.theidlenotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    NotesService notesService;
    @Autowired
    UserService userService;

    @GetMapping("/view-notes")
    public ResponseEntity<List<Notes>> getNotesUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid= authentication.getName();
        Optional<List<Notes>> notesList= notesService.findAllNotesById(userid);
        return new ResponseEntity<>(notesList.get(), HttpStatus.OK);
    }

    @GetMapping("/view-note")
    public ResponseEntity<Notes> getNotesUserById(@RequestHeader String noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid= authentication.getName();
        if(userService.isNoteAvailaible(noteId)) {
            Notes note= notesService.findNoteByID(noteId);
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update-note")
    public ResponseEntity<Notes> updateNotes(@RequestHeader String noteId, @RequestBody Notes note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid= authentication.getName();
        if(userService.isNoteAvailaible(noteId)) {
            notesService.updateNotes(noteId, note);
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete-note")
    public ResponseEntity<Notes> deleteNotes(@RequestHeader String noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        Notes note= notesService.findNoteByID(noteId);
        if(note!=null){
            notesService.deleteNotes(noteId);
            userService.deleteNotes(note);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
