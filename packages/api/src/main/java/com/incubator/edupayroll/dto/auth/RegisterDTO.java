package com.incubator.edupayroll.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RegisterDTO {
    public String name;
    public String email;
    public String title;
    public String password;
    @JsonProperty("school_name")
    public String schoolName;
    @JsonProperty("principal_name")
    public String principalName;
}
