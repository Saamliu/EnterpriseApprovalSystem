package org.example.service;

import org.example.entity.User;
import org.example.response.Result;
import org.example.util.JWTUtil;

import javax.servlet.http.HttpSession;

public interface UserService {
    Result<User> login(User user);

    Result<User> register(User user);

    Result logout();
}
