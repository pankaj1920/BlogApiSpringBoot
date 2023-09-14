package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.CommentSchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentSchema,Integer> {
}
