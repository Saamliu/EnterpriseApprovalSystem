package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/user")
@RestController
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("/login")
    public Object login(User user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public Object register(User user) {
        return userService.register(user);
    }

    @PostMapping ("/logout")
    public Object logout() {
        return userService.logout();
    }
}
