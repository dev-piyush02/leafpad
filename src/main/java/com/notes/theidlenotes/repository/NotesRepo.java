package com.notes.theidlenotes.repository;

import com.notes.theidlenotes.entity.Notes;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotesRepo extends MongoRepository<Notes, String> {

    List<Notes> findAllByUserId(String userId);
}
