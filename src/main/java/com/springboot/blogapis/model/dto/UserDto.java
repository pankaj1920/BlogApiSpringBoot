package com.springboot.blogapis.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4,message = "User name must be min of 4 char")
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 4,max = 10, message = "password must be min of 3 char and max of 10 char")
//    @Pattern(regexp = "")
    private String password;

    @NotEmpty
    private String about;
}
