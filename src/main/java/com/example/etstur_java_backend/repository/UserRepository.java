package com.example.etstur_java_backend.repository;


import com.example.etstur_java_backend.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
    Optional<User> findByUserNameAndPassword(String username,String password);
}
