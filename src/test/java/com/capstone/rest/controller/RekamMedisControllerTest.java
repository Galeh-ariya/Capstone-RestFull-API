package com.capstone.rest.controller;

import com.capstone.rest.entity.RekamMedis;
import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.rekamMedis.CreateRekamMedisRequest;
import com.capstone.rest.model.rekamMedis.response.CreateRekamMedisResponse;
import com.capstone.rest.model.user.response.GetUserResponse;
import com.capstone.rest.repository.RmRepository;
import com.capstone.rest.repository.UserRepository;
import com.capstone.rest.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class RekamMedisControllerTest {

    @Autowired
    private RmRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;


    @BeforeEach
    void setUp() {
        repository.deleteAll();
        userRepository.deleteAll();

        user = new User();
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
    }

    @Test
    void testCreateRekamMedisSuccess() throws Exception {

        CreateRekamMedisRequest request = new CreateRekamMedisRequest();
        request.setAnamnesa("My Silit Is Broken");
        request.setDiagnosis("Silit ee Jebol");
        request.setTherapy("Bapak Phropent");
        request.setJumlahObat(10);

        mockMvc.perform(
                post("/api/rm/K5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<CreateRekamMedisResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            User user = userRepository.findFirstByNoRm(response.getData().getNoRm()).orElse(null);
            assertNotNull(user);
            assertEquals("My Silit Is Broken", response.getData().getAnamnesa());
            RekamMedis rekamMedis = repository.findFirstByUser(user).orElse(null);
            assertEquals(rekamMedis.getDiagnosis(), response.getData().getDiagnosis());
        });

    }

    @Test
    void testCreateRekamMedisUserNotfound() throws Exception {

        CreateRekamMedisRequest request = new CreateRekamMedisRequest();
        request.setAnamnesa("My Silit Is Broken");
        request.setDiagnosis("Silit ee Jebol");
        request.setTherapy("Bapak Phropent");
        request.setJumlahObat(10);

        mockMvc.perform(
                post("/api/rm/salah")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))

        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });

    }

    @Test
    void testListRekamMedisUserNotfound() throws Exception {

        mockMvc.perform(
                get("/api/rm/salah")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });

    }

    @Test
    void testListRekamMedisUserSuccess() throws Exception {

        for (int i = 0; i < 10; i++) {
            RekamMedis rm = new RekamMedis();
            rm.setAnamnesa("sakit " + i);
            rm.setDiagnosis("Diagnosis " + i);
            rm.setTherapy("terapi " + i);
            rm.setUser(user);
            rm.setCreatedAt(LocalDate.now());
            rm.setTotalObat(i);
            repository.save(rm);
        }

        mockMvc.perform(
                get("/api/rm/K5")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<CreateRekamMedisResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
        });

    }
}