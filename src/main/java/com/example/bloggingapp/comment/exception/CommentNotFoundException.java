package com.example.bloggingapp.comment.exception;

import com.example.bloggingapp.common.exception.DomainException;

public class CommentNotFoundException extends DomainException {

    public CommentNotFoundException(String s) {
        super(s);
    }

    public CommentNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
