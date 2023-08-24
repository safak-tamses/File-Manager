package com.example.etstur_java_backend.controller;


import com.example.etstur_java_backend.entity.dto.UserLoginRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserRegisterRequestDTO;
import com.example.etstur_java_backend.service.UserCRUDService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "basicAuth")
@RestController
@RequestMapping("")
@AllArgsConstructor
public class HomePathController {
    private UserCRUDService userCRUDService;

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserRegisterRequestDTO user){
        return new ResponseEntity<>(userCRUDService.saveUser(user), HttpStatusCode.valueOf(200));
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDTO user){
        return new ResponseEntity<>(userCRUDService.userLogin(user),HttpStatusCode.valueOf(200));
    }
    @GetMapping("/loginWithToken")
    public ResponseEntity<?> loginWithToken(@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(userCRUDService.loginWithToken(bearerToken), HttpStatusCode.valueOf(200));
    }
}
