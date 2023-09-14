package com.springboot.blogapis.service.impl;

import com.springboot.blogapis.exception.ResourceNotFoundException;
import com.springboot.blogapis.model.dto.CategoryDto;
import com.springboot.blogapis.model.dto.PostDto;
import com.springboot.blogapis.model.dto.response.PostResponse;
import com.springboot.blogapis.model.entites.CategorySchema;
import com.springboot.blogapis.model.entites.PostSchema;
import com.springboot.blogapis.model.entites.UserSchema;
import com.springboot.blogapis.repositories.CategoryRepo;
import com.springboot.blogapis.repositories.PostRepo;
import com.springboot.blogapis.repositories.UserRepo;
import com.springboot.blogapis.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CategoryRepo categoryRepo;


    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        UserSchema user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));

        CategorySchema category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));


        PostSchema post = this.modelMapper.map(postDto, PostSchema.class);
        post.setImageName("default.png");
        post.setCreatedDate(new Date());
        post.setCategory(category);
        post.setUser(user);

        PostSchema savedPost = this.postRepo.save(post);

        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        PostSchema post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        PostSchema updatePost = this.postRepo.save(post);

        return this.modelMapper.map(updatePost, PostDto.class);

    }

    @Override
    public void deletePost(Integer postId) {

        PostSchema post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        this.postRepo.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = null;
        if (sortDirection.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<PostSchema> pagePost = this.postRepo.findAll(pageable);

        List<PostSchema> postList = pagePost.getContent();

        List<PostDto> postDtoList = postList.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());

        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        PostSchema postSchema = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        return this.modelMapper.map(postSchema, PostDto.class);
    }

    @Override
    public List<PostDto> getPostListByCategory(Integer categoryId) {
        CategorySchema category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));
        List<PostSchema> postList = this.postRepo.findByCategory(category);
        List<PostDto> postDtoList = postList.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostListByUser(Integer userId) {
        UserSchema user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<PostDto> postDtoList = this.postRepo.findByUser(user).stream().map((post) -> this.modelMapper.map(post, PostDto.class)).toList();
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPostByTitle(String keyword) {

        List<PostSchema>postSchemaList = this.postRepo.findByTitleContaining(keyword);
        List<PostDto>postDtoList = postSchemaList.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }

    @Override
    public List<PostDto> searchPostByTitleQuery(String keyword) {

        List<PostSchema>postSchemaList = this.postRepo.findByTitleQuery("%"+keyword+"%");
        List<PostDto>postDtoList = postSchemaList.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoList;
    }
}
