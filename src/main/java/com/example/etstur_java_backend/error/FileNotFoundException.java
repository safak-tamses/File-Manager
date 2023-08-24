package com.example.etstur_java_backend.error;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException() {
        super("File not found!");
    }
}
