package com.springboot.blogapis.service;

import com.springboot.blogapis.model.dto.UserDto;
import org.apache.catalina.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,Integer id);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUser();
    void deleteUser(Integer id);
}
