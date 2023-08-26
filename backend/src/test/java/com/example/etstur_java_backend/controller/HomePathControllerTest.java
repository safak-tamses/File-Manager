package com.example.etstur_java_backend.controller;

import com.example.etstur_java_backend.entity.dao.AuthenticationResponse;
import com.example.etstur_java_backend.entity.dto.LoginWithTokenResponseDTO;
import com.example.etstur_java_backend.entity.dto.UserLoginRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserRegisterRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserResponseDTO;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.service.UserCRUDService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePathControllerTest {
    @InjectMocks
    private HomePathController homePathController;

    @Mock
    private UserCRUDService userCRUDService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveUser() {
        // Arrange
        UserRegisterRequestDTO requestDTO = new UserRegisterRequestDTO();
        // Set up your expected response
        GenericResponse<UserResponseDTO> expectedResponse = GenericResponse.of(new UserResponseDTO("User registration successfully."));
        when(userCRUDService.saveUser(any(UserRegisterRequestDTO.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<GenericResponse> responseEntity = homePathController.saveUser(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void loginUser() {
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO("username", "password");

        // Mock service response
        String jwtToken = "your_generated_jwt_token";  // Replace with an actual JWT token
        GenericResponse<AuthenticationResponse> expectedResponse = GenericResponse.of(new AuthenticationResponse(jwtToken));
        when(userCRUDService.userLogin(any(UserLoginRequestDTO.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<GenericResponse> responseEntity = homePathController.loginUser(requestDTO);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    void loginWithToken() {
        // Arrange
        String bearerToken = "Bearer your_token";  // Replace with an actual token
        String updatedToken = "your_updated_token";  // Replace with an actual updated token
        // Set up your expected response
        GenericResponse<LoginWithTokenResponseDTO> expectedResponse = GenericResponse.of(new LoginWithTokenResponseDTO(updatedToken));
        when(userCRUDService.loginWithToken(any(String.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<GenericResponse> responseEntity = homePathController.loginWithToken(bearerToken);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
