package com.notes.theidlenotes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "idlenotes")
@Data
public class Notes {

    @Id
    @JsonProperty("noteid")
    private String noteId;
    private String title;
    @NonNull
    private String content;
    private Date date;
    @JsonProperty("userid")
    @NonNull
    private String userId;
}
