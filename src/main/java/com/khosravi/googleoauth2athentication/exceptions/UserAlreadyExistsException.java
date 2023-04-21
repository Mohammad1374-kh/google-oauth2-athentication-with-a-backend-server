package com.khosravi.googleoauth2athentication.exceptions;

public class UserAlreadyExistsException extends RuntimeException { public UserAlreadyExistsException() {
        super("exception.user.already.exists");
    }
}
