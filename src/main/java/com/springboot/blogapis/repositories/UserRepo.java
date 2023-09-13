package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserSchema,Integer> {
}
