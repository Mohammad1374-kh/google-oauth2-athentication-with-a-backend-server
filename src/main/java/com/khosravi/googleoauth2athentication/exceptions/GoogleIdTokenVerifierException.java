package com.khosravi.googleoauth2athentication.exceptions;

public class GoogleIdTokenVerifierException extends RuntimeException {
    public GoogleIdTokenVerifierException() {
        super("exception.Google.id.token.verifier");
    }
}
