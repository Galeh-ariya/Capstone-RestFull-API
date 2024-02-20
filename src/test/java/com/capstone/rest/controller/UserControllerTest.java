package com.capstone.rest.controller;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.user.RegisterUserAdminRequest;
import com.capstone.rest.model.user.RegisterUserRequest;
import com.capstone.rest.model.user.response.GetUserResponse;
import com.capstone.rest.model.user.response.RegisterUserResponse;
import com.capstone.rest.repository.RmRepository;
import com.capstone.rest.repository.UserRepository;
import com.capstone.rest.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RmRepository rmRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        rmRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void testRegisterdSuccess() throws Exception {
        RegisterUserAdminRequest adminRequest = new RegisterUserAdminRequest();
        adminRequest.setEmail("3130021003@student.unusa.ac.id");
        adminRequest.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        adminRequest.setName("Galeh");

        mockMvc.perform(
                post("/api/users/admin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminRequest))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });

            assertEquals("OK", response.getData());

        });

    }


    @Test
    void testRegisterdFailed() throws Exception {
        RegisterUserAdminRequest adminRequest = new RegisterUserAdminRequest();
        adminRequest.setEmail("");
        adminRequest.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        adminRequest.setName("");

        mockMvc.perform(
                post("/api/users/admin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminRequest))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });

            assertNotNull(response.getErrors());

        });

    }

    @Test
    void testRegisterDuplicate() throws Exception {

        User user = new User();
        user.setName("Galeh");
        user.setEmail("3130021003@student.unusa.ac.id");
        user.setRole(1);
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userRepository.save(user);


        RegisterUserAdminRequest adminRequest = new RegisterUserAdminRequest();
        adminRequest.setEmail("3130021003@student.unusa.ac.id");
        adminRequest.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        adminRequest.setName("Galeh");

        mockMvc.perform(
                post("/api/users/admin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminRequest))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });

            assertNotNull(response.getErrors());

        });

    }

    @Test
    void testRegisterdUserSuccess() throws Exception {
        User userNew = new User();
        userNew.setName("galeh");
        userNew.setEmail("3130021003@student.unusa.ac.id");
        userNew.setRole(1);
        userNew.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userNew.setToken("test2");
        userNew.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(userNew);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Galehh");
        request.setEmail("3130021002@student.unusa.ac.id");
        request.setGender("laki-laki");
        request.setTempat_lahir("sidoarjo");
        request.setBirthDate("10-04-2002");
        request.setCategory("M");
        request.setInstansi("S1 Sistem Informasi");
        request.setAnamnesa("Mencret 1 Bulan");
        request.setDiagnosis("Silit ee Jebol");
        request.setTherapy("Bapak Prophent");
        request.setTotalObat(10);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<RegisterUserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response);

            assertTrue(userRepository.existsById("3130021003@student.unusa.ac.id"));
            assertNotNull(userRepository.findById("3130021003@student.unusa.ac.id"));
            log.info(response.getData().getPassword());
            log.info(response.getData().getNoRm());
            log.info(response.getData().getCreatedAt().toString());

            User user = userRepository.findById("3130021002@student.unusa.ac.id").orElse(null);
            log.info(user.getBirthDate().toString());

        });

    }

    @Test
    void testRegisterdUserFailed() throws Exception {
        User userNew = new User();
        userNew.setName("galeh");
        userNew.setEmail("3130021003@student.unusa.ac.id");
        userNew.setRole(1);
        userNew.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userNew.setToken("test2");
        userNew.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
        userRepository.save(userNew);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Galehh");
        request.setEmail("");
        request.setGender("laki-laki");
        request.setTempat_lahir("sidoarjo");
        request.setBirthDate("10-04-2002");
        request.setCategory("M");
        request.setInstansi("S1 Sistem Informasi");
        request.setAnamnesa("Mencret 1 Bulan");
        request.setDiagnosis("Silit ee Jebol");
        request.setTherapy("Bapak Prophent");
        request.setTotalObat(10);

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());

        });

    }


    @Test
    void testUserGetButUnauthorized() throws Exception {

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "not")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });

    }

    @Test
    void getNotSendToken() throws Exception {

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });

    }

    @Test
    void getSuccess() throws Exception {
        User user = new User();
        user.setName("test");
        user.setEmail("3130021003@student.unusa.ac.id");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        user.setRole(2);
        user.setGender("laki-laki");
        user.setBirthplace("Sidoarjo");
        user.setBirthDate(LocalDate.now());
        user.setCategory("K");
        user.setInstansi("K3");
        user.setNoRm("K5");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<GetUserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("3130021003@student.unusa.ac.id", response.getData().getEmail());
            assertEquals("test", response.getData().getName());
        });

    }

    @Test
    void getAllSuccess() throws Exception {

        for (int i = 0; i < 8; i++) {
            User user = new User();
            user.setName("test");
            user.setEmail("313002100"+ i +"@student.unusa.ac.id");
            user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
            user.setRole(2);
            user.setGender("laki-laki");
            user.setBirthplace("Sidoarjo");
            user.setBirthDate(LocalDate.now());
            user.setCategory("K");
            user.setInstansi("K3");
            user.setNoRm("K"+i);

            user.setToken("test"+i);
            user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
            userRepository.save(user);
        }

        mockMvc.perform(
                get("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<GetUserResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
        });
    }

    @Test
    void testSearchNotFound() throws Exception{
        mockMvc.perform(
                get("/api/users/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<GetUserResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(0, response.getData().size());
            assertEquals(0, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }

    @Test
    void testSearchByName() throws Exception{
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName("test" + 1);
            user.setEmail("313002100"+ i +"@student.unusa.ac.id");
            user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
            user.setRole(2);
            user.setGender("laki-laki");
            user.setBirthplace("Sidoarjo");
            user.setBirthDate(LocalDate.now());
            user.setCategory("K");
            user.setInstansi("K3");
            user.setNoRm("K"+i);

            user.setToken("test"+i);
            user.setTokenExpiredAt(System.currentTimeMillis() + 1000000L);
            userRepository.save(user);
        }

        mockMvc.perform(
                get("/api/users/search")
                        .queryParam("name", "test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<GetUserResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(10, response.getData().size());
            assertEquals(10, response.getPaging().getTotalPage());
            assertEquals(0, response.getPaging().getCurrentPage());
            assertEquals(10, response.getPaging().getSize());
        });
    }
}