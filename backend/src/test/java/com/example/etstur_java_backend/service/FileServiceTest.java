package com.example.etstur_java_backend.service;


import com.example.etstur_java_backend.entity.dto.FileResponseDTO;
import com.example.etstur_java_backend.entity.enums.Role;
import com.example.etstur_java_backend.entity.model.FileModel;
import com.example.etstur_java_backend.entity.model.User;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.repository.FileRepository;
import com.example.etstur_java_backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class FileServiceTest {

    @Mock
    private JwtService jwtService;
    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCRUDService userCRUDService;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void saveFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "test.png", // Replace with an allowed file name
                "test.png", // Replace with an allowed file name
                "image/png", // Replace with an allowed content type
                "Test content".getBytes() // Replace with actual file content
        );

        String token = "valid_token";
        User mockUser = new User(); // Create a mock user as needed

        when(userCRUDService.findUserToToken(token)).thenReturn(mockUser);
        when(fileRepository.save(any(FileModel.class))).thenReturn(new FileModel());


    }

    @Test
    void getAllFiles() {
    }

    @Test
    void getFileDetails() {
    }

    @Test
    void getFileAsByteArray() {
    }

    @Test
    void updateFile() {
    }

    @Test
    void deieteFile() {
    }
}