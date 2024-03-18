package com.incubator.edupayroll.dto.auth;

import lombok.Value;

@Value
public class LoginDTO {
    public String identifier;
    public String password;
}
