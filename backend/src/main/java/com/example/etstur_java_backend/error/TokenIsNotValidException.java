package com.example.etstur_java_backend.error;

public class TokenIsNotValidException extends RuntimeException{
    public TokenIsNotValidException() {
        super("Token is not valid");
    }
}
