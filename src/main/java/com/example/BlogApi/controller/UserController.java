package com.example.BlogApi.controller;

import com.example.BlogApi.entites.User;
import com.example.BlogApi.payload.UserDto;
import com.example.BlogApi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;

    //POST - create user
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        System.out.printf("############ createUser");
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/abc")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Implementation here
        return ResponseEntity.ok("User created successfully");
    }


    @GetMapping("/test-db-connection")
    public ResponseEntity<String> testDbConnection() {
        try {
            // Attempt to connect to the database here
            // If successful, return a success message
            return ResponseEntity.ok("Database connection test successful");
        } catch (Exception e) {
            // If an exception occurs, return an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error connecting to the database: " + e.getMessage());
        }
    }
}
