package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo  extends JpaRepository<UserSchema,Integer> {
    Optional<UserSchema> findByEmail(String email);
}
