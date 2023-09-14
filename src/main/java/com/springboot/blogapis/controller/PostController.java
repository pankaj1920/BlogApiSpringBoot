package com.springboot.blogapis.controller;

import com.springboot.blogapis.api_response.ApiResponse;
import com.springboot.blogapis.constant.AppConstant;
import com.springboot.blogapis.model.dto.PostDto;
import com.springboot.blogapis.model.dto.response.PostResponse;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.service.FileService;
import com.springboot.blogapis.service.PostService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String path;

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
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable(name = "categoryId") Integer categoryId) {
        List<PostDto> postDtoList = this.postService.getPostListByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AppConstant.SORT_DIRECTION, required = false) String sortDirection
    ) {
        PostResponse postDtoList = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/getPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(true, "Post deleted successfully"), HttpStatus.OK);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "postId") Integer postId) {
        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
            @RequestParam(value = "postTitle", required = true) String postTitle
    ) {
        List<PostDto> postDtoList = this.postService.searchPostByTitle(postTitle);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("searchQuery")
    public ResponseEntity<List<PostDto>> searchPostByTitleQuery(
            @RequestParam(value = "postTitle", required = true) String postTitle
    ) {
        List<PostDto> postDtoList = this.postService.searchPostByTitleQuery(postTitle);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    //Post Image Upload
    @PostMapping("image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam(name = "image") MultipartFile image,
            @PathVariable(name = "postId") Integer postId
    ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        System.out.println("fileName "+fileName);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);

    }

    // method to serve file
    @GetMapping(value = "image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
