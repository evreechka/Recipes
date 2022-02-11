package com.mari.recipes.controllers;

import com.mari.recipes.models.User;
import com.mari.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/register", consumes="application/json")
    public void registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user.getEmail(), user.getPassword());
    }
}
