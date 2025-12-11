package com.company.lims.authuserservice.controller;

import com.company.lims.authuserservice.dto.request.RegisterRequest;
import com.company.lims.authuserservice.dto.response.RegisterResponse;
import com.company.lims.authuserservice.dto.response.UserResponse;
import com.company.lims.authuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/users")
public class UserController {
    UserService  userService;

    @PostMapping("/register")
    public RegisterResponse createUser (@RequestBody RegisterRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return userService.getAllUsers();
    }
}
