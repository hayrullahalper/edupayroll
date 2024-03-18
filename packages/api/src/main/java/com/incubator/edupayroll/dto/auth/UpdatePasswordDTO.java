package com.incubator.edupayroll.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UpdatePasswordDTO {

    public String password;
    @JsonProperty("new_password")
    public String newPassword;

}
