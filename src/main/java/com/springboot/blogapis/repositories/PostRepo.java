package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.CategorySchema;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.model.entites.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<PostSchema,Integer> {

    List<PostSchema> findByUser(UserSchema userSchema);
    List<PostSchema> findByCategory(CategorySchema categorySchema);
}
