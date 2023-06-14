package com.example.bloggingapp.user.exception;

import com.example.bloggingapp.common.exception.DomainException;

import javax.validation.constraints.NotNull;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
