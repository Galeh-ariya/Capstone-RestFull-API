package com.capstone.rest.service;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.user.UserLoginRequest;
import com.capstone.rest.model.user.response.LoginUserResponse;
import com.capstone.rest.repository.UserRepository;
import com.capstone.rest.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ValidationService validator;

    @Transactional
    public LoginUserResponse login(UserLoginRequest request) {
        validator.validate(request);

        User user = repository.findById(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email or Password Wrong"));

        if(BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            user.setToken(UUID.randomUUID().toString());
            user.setTokenExpiredAt(next30D());
            repository.save(user);

            return LoginUserResponse.builder()
                    .token(user.getToken())
                    .tokenExpiredAt(user.getTokenExpiredAt())
                    .role(user.getRole())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or Password Wrong");
        }

    }

    @Transactional
    public void logout(User user) {
        user.setToken(null);
        user.setTokenExpiredAt(null);

        repository.save(user);
    }

    private Long next30D() {
        return System.currentTimeMillis() + (1000 * 60 * 24 * 30);
    }

}
