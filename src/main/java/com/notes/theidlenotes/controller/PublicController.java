package com.notes.theidlenotes.controller;

import com.notes.theidlenotes.entity.LoginUserDetail;
import com.notes.theidlenotes.entity.Notes;
import com.notes.theidlenotes.entity.User;
import com.notes.theidlenotes.service.NotesService;
import com.notes.theidlenotes.service.UserDetailServiceImpl;
import com.notes.theidlenotes.service.UserService;
import com.notes.theidlenotes.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private NotesService notesService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception{
        if(user.getRoles().isEmpty()){
            user.getRoles().add("USER");
        }
        try {
            if (userService.addUser(user)) {
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDetail user, HttpServletResponse response) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserId());
            String jwtToken = jwtUtil.generateToken(userDetails.getUsername());
            // code for generating a cookie valid for 30 mins
            ResponseCookie cookie = ResponseCookie.from("access_token", jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(60*30)
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>("Welcome my dear friend", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Incorrect username or password",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add-note")
    public ResponseEntity<Notes> createNotes(@RequestBody Notes note) {
        if(userService.findUserByUserID(note.getUserId()).isPresent()){
            notesService.createNotes(note);
            userService.addNotes(note);
            return new ResponseEntity<>(note, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("access_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }
}
