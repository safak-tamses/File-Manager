package com.example.etstur_java_backend.service;

import com.example.etstur_java_backend.entity.dao.AuthenticationResponse;
import com.example.etstur_java_backend.entity.dto.LoginWithTokenResponseDTO;
import com.example.etstur_java_backend.entity.dto.UserLoginRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserRegisterRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserResponseDTO;
import com.example.etstur_java_backend.entity.model.User;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserCRUDServiceTest {
    private UserRepository userRepository;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserCRUDService userCRUDService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userCRUDService = new UserCRUDService(userRepository, jwtService, authenticationManager, passwordEncoder);
    }
    @Test
    void saveUser() {
        UserRegisterRequestDTO requestDTO = new UserRegisterRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setPassword("password");

        when(userRepository.findByUserName(requestDTO.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("encodedPassword");

        GenericResponse<?> response = userCRUDService.saveUser(requestDTO);

        assertNotNull(response);
        assertTrue(response.getData() instanceof UserResponseDTO);
        assertEquals("User registration successfully.", ((UserResponseDTO) response.getData()).getMessage());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void userLogin() {
        UserLoginRequestDTO requestDTO = new UserLoginRequestDTO();
        requestDTO.setUsername("testuser");
        requestDTO.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByUserName(requestDTO.getUsername()))
                .thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        GenericResponse<?> response = userCRUDService.userLogin(requestDTO);

        assertNotNull(response);
        assertTrue(response.getData() instanceof AuthenticationResponse);
        assertEquals("jwtToken", ((AuthenticationResponse) response.getData()).getToken());
    }

    @Test
    void findUserToToken() throws Exception {
        String bearerToken = "Bearer jwtToken";

        when(jwtService.extractUsername("jwtToken")).thenReturn("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(new User()));

        User user = userCRUDService.findUserToToken(bearerToken);

        assertNotNull(user);
    }

    @Test
    void loginWithToken() {
        String bearerToken = "Bearer jwtToken";

        when(jwtService.extractUsername("jwtToken")).thenReturn("testuser");
        when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any(User.class))).thenReturn("newJwtToken");

        GenericResponse<?> response = userCRUDService.loginWithToken(bearerToken);

        assertNotNull(response);
        assertTrue(response.getData() instanceof LoginWithTokenResponseDTO);
        assertEquals("newJwtToken", ((LoginWithTokenResponseDTO) response.getData()).getUpdatedToken());
    }
}