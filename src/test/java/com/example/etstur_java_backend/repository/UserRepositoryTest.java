package com.example.etstur_java_backend.repository;

import com.example.etstur_java_backend.entity.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Disable auto-configuration of embedded database
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void findByUserName() {
        // Create a sample user
        User user = new User();
        user.setUserName("testuser");
        // Set other user properties...

        // Save the user
        userRepository.save(user);

        // Search for the user by username
        Optional<User> foundUserOptional = userRepository.findByUserName("testuser");

        // Check if the user was found
        assertTrue(foundUserOptional.isPresent(), "User not found");

        // More detailed assertions to check user properties
        User foundUser = foundUserOptional.get();
        assertEquals("testuser", foundUser.getUsername(), "Username does not match");
    }
}