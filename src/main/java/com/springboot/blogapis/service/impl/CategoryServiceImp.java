package com.springboot.blogapis.service.impl;

import com.springboot.blogapis.exception.ResourceNotFoundException;
import com.springboot.blogapis.model.dto.CategoryDto;
import com.springboot.blogapis.model.entites.CategorySchema;
import com.springboot.blogapis.repositories.CategoryRepo;
import com.springboot.blogapis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategorySchema categorySchema = this.modelMapper.map(categoryDto, CategorySchema.class);
        CategorySchema savedCategory = this.categoryRepo.save(categorySchema);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        CategorySchema oldCategory = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category ", "CategoryId", categoryId));
        oldCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        oldCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        CategorySchema updateCategory = this.categoryRepo.save(oldCategory);
        return this.modelMapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        CategorySchema category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        this.categoryRepo.delete(category);

    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        CategorySchema categorySchema = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        return this.modelMapper.map(categorySchema, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategoryList() {
        List<CategorySchema> categoryList = this.categoryRepo.findAll();
        return categoryList.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

    }
}
