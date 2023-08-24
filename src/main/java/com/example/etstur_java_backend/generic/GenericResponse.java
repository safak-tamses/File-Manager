package com.example.etstur_java_backend.generic;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class GenericResponse<T> {
    private T data;
    private Date responseDate;
    private Boolean isSuccess;

    public GenericResponse(T data, Boolean isSuccess) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.responseDate = new Date();
    }

    public static <T> GenericResponse <T> of(T data){
        return new GenericResponse<>(data,true);
    }
    public static <T> GenericResponse <T> error(T data){
        return new GenericResponse<>(data,false);
    }
}
