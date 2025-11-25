package com.notes.theidlenotes.repository;

import com.notes.theidlenotes.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String>{

}
