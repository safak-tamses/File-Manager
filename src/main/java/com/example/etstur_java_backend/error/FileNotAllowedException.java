package com.example.etstur_java_backend.error;

public class FileNotAllowedException extends RuntimeException{
    public FileNotAllowedException() {
        super("The file is not in a valid format please try again");
    }
}
