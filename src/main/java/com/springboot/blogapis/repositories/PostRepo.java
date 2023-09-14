package com.springboot.blogapis.repositories;

import com.springboot.blogapis.model.entites.CategorySchema;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.model.entites.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<PostSchema,Integer> {

    List<PostSchema> findByUser(UserSchema userSchema);
    List<PostSchema> findByCategory(CategorySchema categorySchema);

    List<PostSchema> findByTitleContaining(String title);

    @Query("select p from PostSchema p where p.title like :key")
    List<PostSchema> findByTitleQuery(@Param("key") String title);
}
