package com.springboot.blogapis.model.dto;

import com.springboot.blogapis.model.entites.PostSchema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private int id;
    private String content;

}
