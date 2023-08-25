package com.example.etstur_java_backend.error;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException() {
        super("Login failed.");
    }
}
