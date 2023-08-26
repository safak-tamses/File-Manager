package com.example.etstur_java_backend.controller;


import com.example.etstur_java_backend.service.FileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/user/files")
public class FileController {
    private final FileService fileService;
    @PostMapping()
    public ResponseEntity<?> saveFile(@RequestParam(value = "file") MultipartFile file , @RequestHeader("Authorization") String bearerToken) throws Exception {
        return new ResponseEntity<>(fileService.saveFile(file,bearerToken), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getDetailsOfAllFiles(@RequestHeader("Authorization") String bearerToken){
        return new ResponseEntity<>(fileService.getAllFiles(bearerToken),HttpStatus.OK);
    }
    @GetMapping("/{id}/details")
    public ResponseEntity<?>getFileDetailsById(@RequestHeader("Authorization") String bearerToken,@PathVariable Long id){
        return new ResponseEntity<>(fileService.getFileDetails(bearerToken,id),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity getContentOfFileAsArrayList(@RequestHeader("Authorization") String bearerToken,@PathVariable Long id){
        return new ResponseEntity<>(fileService.getFileAsByteArray(bearerToken,id),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFile(@RequestParam(value = "file") MultipartFile file, @RequestHeader("Authorization") String bearerToken, @PathVariable Long id){
        return new ResponseEntity<>(fileService.updateFile(bearerToken,id,file),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFile(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id){
        return new ResponseEntity<>(fileService.deieteFile(bearerToken,id),HttpStatus.OK);
    }



}
