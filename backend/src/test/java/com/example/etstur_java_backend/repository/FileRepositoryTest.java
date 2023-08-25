package com.example.etstur_java_backend.repository;

import com.example.etstur_java_backend.entity.model.FileModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    @Sql("/data.sql") // You can provide a SQL script to populate test data
    public void testFindFileByUserId() {
        Long userId = 1L; // Replace with an actual user ID from your data.sql

        List<FileModel> files = fileRepository.findFileByUserId(userId);

        assertNotNull(files, "Files should not be null");
        assertTrue(files.size() > 0, "No files found for the user");
        // Add more assertions to validate the returned files if needed
    }

    @Test
    public void testSaveFileModel() {
        FileModel file = new FileModel();
        // Set file properties...

        FileModel savedFile = fileRepository.save(file);

        assertNotNull(savedFile, "Saved file should not be null");
        // Add more assertions to validate the saved file if needed
    }
    @AfterEach
    @Sql("/cleanup.sql") // SQL script to clean up test data after each test method
    public void cleanup() {
        // This method will be executed after each test method
        // It will use the cleanup.sql script to delete test data
    }
}