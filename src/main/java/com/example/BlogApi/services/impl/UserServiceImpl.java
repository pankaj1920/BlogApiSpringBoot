package com.example.BlogApi.services.impl;

import com.example.BlogApi.entites.User;
import com.example.BlogApi.exception.ResourceNotFoundException;
import com.example.BlogApi.payload.UserDto;
import com.example.BlogApi.repositories.UserRepo;
import com.example.BlogApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = this.userRepo.save(dtoToUser(userDto));
        return userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {

        User userInfo = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        userInfo.setName(userDto.getName());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setPassword(userDto.getPassword());
        userInfo.setAbout(userDto.getAbout());

        User updateUser = this.userRepo.save(userInfo);
        this.userToUserDto(updateUser);

        return null;
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.userRepo.findAll();

        return userList.stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {

        User userInfo = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(userInfo);
    }

    private User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
    }

    private UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
