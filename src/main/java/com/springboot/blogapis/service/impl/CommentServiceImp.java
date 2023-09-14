package com.springboot.blogapis.service.impl;

import com.springboot.blogapis.exception.ResourceNotFoundException;
import com.springboot.blogapis.model.dto.CommentDto;
import com.springboot.blogapis.model.entites.CommentSchema;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.repositories.CommentRepo;
import com.springboot.blogapis.repositories.PostRepo;
import com.springboot.blogapis.service.CommentService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        PostSchema post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        CommentSchema commentSchema = this.modelMapper.map(commentDto, CommentSchema.class);
        commentSchema.setPost(post);
      CommentSchema savedComment =  this.commentRepo.save(commentSchema);

        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        CommentSchema commentSchema = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","comment_id",commentId));
        this.commentRepo.delete(commentSchema);
    }
}
