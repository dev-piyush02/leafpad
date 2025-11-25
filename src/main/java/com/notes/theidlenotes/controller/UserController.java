package com.notes.theidlenotes.controller;

import com.notes.theidlenotes.entity.User;
import com.notes.theidlenotes.repository.UserRepo;
import com.notes.theidlenotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/view-user")
    public ResponseEntity<Optional<User>> findById(@RequestParam String userid){
        Optional<User> usr= userService.findUserByUserID(userid);
        if(usr.isPresent())
            return new ResponseEntity<>(userService.findUserByUserID(userid), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        User userInDB= userService.findUserByUserID(userId).get();
        if(userInDB!=null){
            userInDB.setPassword(user.getPassword());
            userInDB.setFirstName(user.getFirstName());
            userInDB.setLastName(user.getLastName());
            userInDB.setEmail(user.getEmail());
            userRepo.save(userInDB);
            return new ResponseEntity<>(userInDB, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
