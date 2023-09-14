package com.springboot.blogapis.model.dto;

import com.springboot.blogapis.model.entites.CategorySchema;
import com.springboot.blogapis.model.entites.CommentSchema;
import com.springboot.blogapis.model.entites.UserSchema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private String postId;
    private String title;
    private String content;
    private String imageName;
    private Date createdDate;

    private CategoryDto category;
    private UserDto user;

    private Set<CommentDto> comment = new HashSet<>();
}
