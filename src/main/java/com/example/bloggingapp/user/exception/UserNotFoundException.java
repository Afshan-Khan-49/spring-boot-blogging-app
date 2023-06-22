package com.example.bloggingapp.user.exception;

import com.example.bloggingapp.common.exception.DomainException;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(String s) {
        super(s);
    }

}
