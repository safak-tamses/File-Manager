package com.example.etstur_java_backend.controller;

import com.example.etstur_java_backend.entity.dto.FileResponseDTO;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FileControllerTest {
    @InjectMocks
    private FileController fileController;

    @Mock
    private FileService fileService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveFile() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.pdf", "text/plain", "File content".getBytes());
        GenericResponse<FileResponseDTO> response = GenericResponse.of(FileResponseDTO.builder()
                .message("File saved successfully.")
                .build());
        when(fileService.saveFile(any(MockMultipartFile.class), any(String.class))).thenReturn(response);

        // Act
        ResponseEntity<?> responseEntity = fileController.saveFile(file, "your_mocked_token");

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getDetailsOfAllFiles() {

    }

    @Test
    void getFileDetailsById() {
    }

    @Test
    void getContentOfFileAsArrayList() {
    }

    @Test
    void updateFile() {
    }

    @Test
    void deleteFile() {
    }
}