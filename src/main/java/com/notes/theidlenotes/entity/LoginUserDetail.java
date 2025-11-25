package com.notes.theidlenotes.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginUserDetail {
    @JsonProperty("userid")
    protected String userId;
    protected String password;
}
