package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.CategorySchema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategorySchema,Integer> {

}
