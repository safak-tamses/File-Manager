package com.example.etstur_java_backend.error;

public class FileSizeLimitExceededException extends RuntimeException{
    public FileSizeLimitExceededException() {
        super("File size limit exceeded");
    }
}
