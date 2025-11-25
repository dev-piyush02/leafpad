package com.notes.theidlenotes.service;

import com.notes.theidlenotes.entity.Notes;
import com.notes.theidlenotes.entity.User;
import com.notes.theidlenotes.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private NotesService notesService;
    public static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> findAllUsers(){
        return userRepo.findAll();
    }

    public Optional<User> findUserByUserID(String userId){
        return userRepo.findById(userId);
    }

    public boolean addUser(User user) throws NoSuchAlgorithmException {
        if(!userRepo.findById(user.getUserId()).isPresent()) {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return true;
        }
        else
            return false;
    }

    public boolean deleteUserById(String userId){
        if(userRepo.findById(userId).isPresent()) {
            userRepo.deleteById(userId);
            return true;
        }
        else
            return false;

    }

    public void addNotes(Notes note) {
        String userId = note.getUserId();
        User userInDB= findUserByUserID(userId).get();
        userInDB.getNotes().add(note);
        userRepo.save(userInDB);
    }

    public void deleteNotes(Notes note) {
        String userId = note.getUserId();
        User userInDB= findUserByUserID(userId).get();
        userInDB.getNotes().remove(note);
        userRepo.save(userInDB);
    }
    public boolean isNoteAvailaible(String noteId){
        Notes note= notesService.findNoteByID(noteId);
        if(findUserByUserID(note.getUserId()).get().getNotes().contains(note)){
            return true;
        }
        else
            return false;
    }
}
