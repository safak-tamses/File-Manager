package com.example.etstur_java_backend.error;

public class FileDoesNotBelongToUserException extends RuntimeException{
    public FileDoesNotBelongToUserException() {
        super("File does not belong to user.");
    }
}
