package com.example.bloggingapp.post.exception;

import com.example.bloggingapp.common.exception.DomainException;

public class PostNotFoundException extends DomainException {
    public PostNotFoundException(String s) {
        super(s);
    }

}
