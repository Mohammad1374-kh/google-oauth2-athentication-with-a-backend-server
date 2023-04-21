package com.khosravi.googleoauth2athentication.exceptions;

public class InvalidGoogleBearerTokenException extends RuntimeException {
    public InvalidGoogleBearerTokenException() {
        super("exception.Google.authorization.bearer.format.invalid");
    }
}
