package com.khosravi.googleoauth2athentication.exceptions;

public class UserNotFoundException extends RuntimeException { public UserNotFoundException() {
        super("exception.user.not.found");
    }
}
