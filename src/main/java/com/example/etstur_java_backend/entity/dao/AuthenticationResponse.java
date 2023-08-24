package com.example.etstur_java_backend.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
