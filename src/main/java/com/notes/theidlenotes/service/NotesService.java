package com.notes.theidlenotes.service;

import com.notes.theidlenotes.entity.Notes;
import com.notes.theidlenotes.repository.NotesRepo;
import com.notes.theidlenotes.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    private NotesRepo notesRepo;
    @Autowired
    private UserRepo userRepo;

    public Optional<List<Notes>> findAllNotesById(String userId){
         List<Notes> allNotes= notesRepo.findAllByUserId(userId);
         return Optional.of(allNotes);
    }

    public boolean createNotes(Notes notes) {
        if(notes.getDate()==null){
            notes.setDate(new Date());
        }
        notesRepo.save(notes);
        return true;
    }

    public boolean deleteNotes(String notesId) {
        if(notesRepo.existsById(notesId)){
            notesRepo.deleteById(notesId);
            return true;
        }
        return false;
    }

    public Optional<Notes> updateNotes(String notesId, Notes notes) {
        Notes oldNote= notesRepo.findById(notesId).get();
            oldNote.setTitle(notes.getTitle());
            oldNote.setContent(notes.getContent());
            oldNote.setDate(new Date());
            notesRepo.save(oldNote);
        return Optional.of(oldNote);
    }

    public Notes findNoteByID(String noteId){
        return notesRepo.findById(noteId).get();
    }
}
