package com.notes.theidlenotes.service;

import com.notes.theidlenotes.entity.User;
import com.notes.theidlenotes.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user= userRepo.findById(userId).get();
        if(user==null)
            throw new UsernameNotFoundException(userId);
        return org.springframework.security.core.userdetails.User.builder()
                .username(userId)
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
