package com.springboot.blogapis.service;

import com.springboot.blogapis.model.dto.PostDto;
import com.springboot.blogapis.model.entites.PostSchema;

import java.util.List;

public interface PostService{

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void deletePost(Integer postId);

    List<PostDto> getAllPost();

    PostDto getPostById(Integer postId);

    //Get all post by category
    List<PostDto> getPostListByCategory(Integer categoryId);

    // Get all post by user
    List<PostDto> getPostListByUser(Integer userId);

    // Search Post
    List<PostDto> searchPost(String keyword);



}
