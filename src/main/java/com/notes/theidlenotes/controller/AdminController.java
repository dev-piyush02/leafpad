package com.notes.theidlenotes.controller;

import com.notes.theidlenotes.entity.User;
import com.notes.theidlenotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        User userInDB= userService.findUserByUserID(user.getUserId()).get();
        userInDB.getRoles().add("ADMIN");
       try{
           userService.addUser(userInDB);
           return new ResponseEntity<>(userInDB, HttpStatus.OK);
       }
       catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
}
