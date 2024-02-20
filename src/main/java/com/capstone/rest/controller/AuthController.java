package com.capstone.rest.controller;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.user.UserLoginRequest;
import com.capstone.rest.model.user.response.LoginUserResponse;
import com.capstone.rest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(
            path = "/api/auth/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<LoginUserResponse> login(@RequestBody UserLoginRequest request) {
        LoginUserResponse login = service.login(request);
        return WebResponse.<LoginUserResponse>builder()
                .data(login)
                .build();
    }


    @DeleteMapping(
            path = "/api/auth/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user) {
        service.logout(user);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

}
