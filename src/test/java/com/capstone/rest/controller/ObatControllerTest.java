package com.capstone.rest.controller;

import com.capstone.rest.entity.Obat;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.obat.CreateObatRequest;
import com.capstone.rest.model.obat.UpdateObatRequest;
import com.capstone.rest.model.obat.response.ObatResponse;
import com.capstone.rest.model.user.response.LoginUserResponse;
import com.capstone.rest.repository.ObatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ObatControllerTest {

    @Autowired
    private ObatRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @BeforeEach
//    void setUp() {
//        repository.deleteAll();
//    }

    @Test
    void testCreateFailed() throws Exception {
        CreateObatRequest request = new CreateObatRequest();
        request.setNameObat("");
        request.setExpired("10-10-2025");
        request.setStock(100);
        request.setMinStock(20);

        mockMvc.perform(
                post("/api/obat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
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
    void testCreateSuccess() throws Exception {
        CreateObatRequest request = new CreateObatRequest();
        request.setNameObat("Bapak Phropent");
        request.setExpired("10-10-2025");
        request.setStock(100);
        request.setMinStock(20);

        mockMvc.perform(
                post("/api/obat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<ObatResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("Bapak Phropent", response.getData().getNameObat());
            assertEquals(0, response.getData().getUsedTotal());
        });
    }

    @Test
    void testListSuccess() throws Exception {
        for (int i = 0; i < 2; i++) {
            Obat obat = new Obat();
            obat.setNameObat("Bapak-"+i+" Prhopent"+i);
            obat.setExpiredAt(LocalDate.now());
            obat.setStock(100+i);
            obat.setMintStock(20);
            obat.setUsedTotal(0);
            repository.save(obat);
        }

        mockMvc.perform(
                get("/api/obat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<ObatResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
        });
    }

//    @Test
//    void testUpdateSuccess() throws Exception {
//        Obat obat = new Obat();
//        obat.setNameObat("BapakPrhopent");
//        obat.setExpiredAt(LocalDate.now());
//        obat.setStock(100);
//        obat.setMintStock(20);
//        obat.setUsedTotal(0);
//        repository.save(obat);
//
//        UpdateObatRequest request = new UpdateObatRequest();
//        request.setIdObat(15);
//        request.setNameObat("Bapak Phropent");
//        request.setExpired("10-10-2025");
//        request.setStock(100);
//        request.setMinStock(20);
//
//
//        mockMvc.perform(
//                patch("/api/obat")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request))
//        ).andExpectAll(
//                status().isOk()
//        ).andDo(result -> {
//            WebResponse<ObatResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
//            });
//
//            assertNull(response.getErrors());
//            assertEquals("Bapak Phropent", response.getData().getNameObat());
//            assertEquals(0, response.getData().getUsedTotal());
//        });
//    }

}