package com.springboot.blogapis.service.impl;

import com.springboot.blogapis.exception.ResourceNotFoundException;
import com.springboot.blogapis.model.dto.UserDto;
import com.springboot.blogapis.model.entites.UserSchema;
import com.springboot.blogapis.repositories.UserRepo;
import com.springboot.blogapis.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        UserSchema userSchema = this.userRepo.save(dtoToUser(userDto));
        return userToUserDto(userSchema);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {

        UserSchema userInfo = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", " Id ",id));
        userInfo.setName(userDto.getName());
        userInfo.setEmail(userDto.getEmail());
        userInfo.setPassword(userDto.getPassword());
        userInfo.setAbout(userDto.getAbout());

        UserSchema updateUser =this.userRepo.save(userInfo);

        return this.userToUserDto(updateUser);
    }

    @Override
    public UserDto getUserById(Integer id) {

        UserSchema user = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserSchema> userList = this.userRepo.findAll();

        return userList.stream().map(user -> userToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
        UserSchema userInfo = this.userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
        this.userRepo.delete(userInfo);
    }

    private UserSchema dtoToUser(UserDto userDto) {

       /* UserSchema user = new UserSchema();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;*/

       return this.modelMapper.map( userDto , UserSchema.class);
    }

    private UserDto userToUserDto(UserSchema user) {
        /*UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;*/
        return this.modelMapper.map(user, UserDto.class);
    }
}
