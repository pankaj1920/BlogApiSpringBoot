package com.springboot.blogapis.controller;

import com.springboot.blogapis.api_response.ApiResponse;
import com.springboot.blogapis.model.dto.UserDto;
import com.springboot.blogapis.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        System.out.printf("############ createUser");
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping("updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser( @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto user = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("deleteUser/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse(true, "User deleted success"), HttpStatus.OK);
    }

    @GetMapping("getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("getUser/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }
}
