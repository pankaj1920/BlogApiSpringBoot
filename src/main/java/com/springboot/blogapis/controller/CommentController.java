package com.springboot.blogapis.controller;

import com.springboot.blogapis.api_response.ApiResponse;
import com.springboot.blogapis.model.dto.CommentDto;
import com.springboot.blogapis.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("createComment/{postId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(name = "postId") Integer postId
    ) {
        CommentDto commentResponse = this.commentService.createComment(commentDto, postId);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("deleteComment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "commentId") Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse(true, "Comment deleted succesfully"), HttpStatus.OK);
    }
}
