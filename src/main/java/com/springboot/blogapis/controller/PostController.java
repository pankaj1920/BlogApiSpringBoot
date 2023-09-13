package com.springboot.blogapis.controller;

import com.springboot.blogapis.api_response.ApiResponse;
import com.springboot.blogapis.model.dto.PostDto;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable(name = "userId") Integer userId,
            @PathVariable(name = "categoryId") Integer categoryId) {

        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/getPost")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable(name = "userId") Integer userId) {
        List<PostDto> postDtoList = this.postService.getPostListByUser(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/getPost")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable(name = "categoryId") Integer categoryId){
        List<PostDto> postDtoList = this.postService.getPostListByCategory(categoryId);
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> postDtoList = this.postService.getAllPost();
        return new ResponseEntity<>(postDtoList,HttpStatus.OK);
    }

    @GetMapping("/getPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Integer postId){
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(true,"Post deleted successfully"),HttpStatus.OK);
    }


}
