package com.example.etstur_java_backend.service;

import com.example.etstur_java_backend.entity.dao.AuthenticationResponse;
import com.example.etstur_java_backend.entity.dto.LoginWithTokenResponseDTO;
import com.example.etstur_java_backend.entity.dto.UserLoginRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserRegisterRequestDTO;
import com.example.etstur_java_backend.entity.dto.UserResponseDTO;
import com.example.etstur_java_backend.entity.enums.Role;
import com.example.etstur_java_backend.entity.model.User;
import com.example.etstur_java_backend.error.GenericExceptionResponse;
import com.example.etstur_java_backend.error.LoginFailedException;
import com.example.etstur_java_backend.error.TokenIsNotValidException;
import com.example.etstur_java_backend.error.UserAlreadyExistsException;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCRUDService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public GenericResponse<UserResponseDTO> saveUser(UserRegisterRequestDTO user) {
        Optional<User> testUser = userRepository.findByUserName(user.getUsername());

        if (testUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        User newUser = User.builder()
                .userName(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(newUser);
        return GenericResponse.of(UserResponseDTO.builder()
                .message("User registration successfully.")
                .build());
    }

    public GenericResponse<AuthenticationResponse> userLogin(UserLoginRequestDTO authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
            var user = userRepository.findByUserName(authenticationRequest.getUsername())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);

            return GenericResponse.of(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        } catch (Exception e){
            throw new LoginFailedException();
        }

    }

    public User findUserToToken(String bearerToken) throws Exception {
        try {
            bearerToken = bearerToken.substring(7);
            final String userName = jwtService.extractUsername(bearerToken);
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new Exception("Kullanıcı bulunamadı "));
            return user;
        } catch (Exception e) {
            throw new Exception("Token problem");
        }
    }

    public GenericResponse loginWithToken(String bearerToken) {
        try {
            bearerToken = bearerToken.substring(7);
            final String userName = jwtService.extractUsername(bearerToken);
            User user = userRepository.findByUserName(userName).orElseThrow(() -> new TokenIsNotValidException());
            String updatedToken = jwtService.generateToken(user);
            return GenericResponse.of(LoginWithTokenResponseDTO.builder()
                    .updatedToken(updatedToken)
                    .build());
        } catch (TokenIsNotValidException | ExpiredJwtException e) {
            throw new TokenIsNotValidException();
        }
    }
}
