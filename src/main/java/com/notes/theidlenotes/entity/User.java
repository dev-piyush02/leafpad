package com.notes.theidlenotes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document (collection = "idleusers")
@Data
public class User {

    @Id
    @JsonProperty("userid")
    private String userId;
    @NonNull
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String password; //used only for viewing all notes
    @DBRef
    private List<Notes> notes= new ArrayList<>();
    List<String> roles  = new ArrayList<>();
}