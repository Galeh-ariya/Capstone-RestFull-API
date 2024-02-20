package com.capstone.rest.controller;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.user.UserLoginRequest;
import com.capstone.rest.model.user.response.LoginUserResponse;
import com.capstone.rest.model.user.response.RegisterUserResponse;
import com.capstone.rest.repository.UserRepository;
import com.capstone.rest.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testLoginFailed() throws Exception {
        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("3130021003@student.unusa.ac.id");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testLoginPasswordWrong() throws Exception {
        User user = new User();
        user.setEmail("3130021003@student.unusa.ac.id");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setRole(1);
        userRepository.save(user);

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("3130021007@student.unusa.ac.id");
        request.setPassword("salah");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testLoginSuccess() throws Exception {
        User user = new User();
        user.setEmail("3130021003@student.unusa.ac.id");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setRole(1);
        userRepository.save(user);

        UserLoginRequest request = new UserLoginRequest();
        request.setEmail("3130021003@student.unusa.ac.id");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<LoginUserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertNotNull(response.getData().getEmail());
            assertNotNull(response.getData().getName());
            assertNotNull(response.getData().getRole());
            assertNotNull(response.getData().getToken());
            assertNotNull(response.getData().getTokenExpiredAt());

            User user1 = userRepository.findById("3130021003@student.unusa.ac.id").orElse(null);
            assertNotNull(user1);
            assertEquals(user1.getToken(), response.getData().getToken());
        });
    }

    @Test
    void testLogout() throws Exception {
        User user = new User();
        user.setEmail("3130021003@student.unusa.ac.id");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setName("Test");
        user.setRole(1);
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 100000000L);
        userRepository.save(user);


        mockMvc.perform(
                delete("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("OK", response.getData());

            User dbu = userRepository.findById("3130021003@student.unusa.ac.id").orElse(null);
            assertNull(dbu.getToken());
        });
    }

}
