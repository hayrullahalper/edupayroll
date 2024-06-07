package com.incubator.edupayroll.util.response;

import lombok.Value;

@Value
public class ResponseError<C> {
    public C code;
    public String message;
}
