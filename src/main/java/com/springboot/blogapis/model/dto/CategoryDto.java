package com.springboot.blogapis.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    public Integer categoryId;
    @NotBlank
    @Size(min = 4)
    public String categoryTitle;

    @NotBlank
    @Size(min = 10)
    public String categoryDescription;
}
