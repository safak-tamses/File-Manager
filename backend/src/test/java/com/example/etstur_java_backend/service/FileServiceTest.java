package com.example.etstur_java_backend.service;


import com.example.etstur_java_backend.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class FileServiceTest {
    @Mock
    private FileRepository fileRepository;

    @Mock
    private UserCRUDService userCRUDService;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void saveFile() {

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