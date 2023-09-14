package com.springboot.blogapis.service;

import com.springboot.blogapis.model.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);

}
