package com.capstone.rest.controller;

import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.obat.CreateObatRequest;
import com.capstone.rest.model.obat.UpdateObatRequest;
import com.capstone.rest.model.obat.response.ObatResponse;
import com.capstone.rest.service.ObatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ObatController {

    @Autowired
    private ObatService service;

    @PostMapping(
            path = "/api/obat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ObatResponse> create(@RequestBody CreateObatRequest request) {
        ObatResponse response = service.create(request);
        return WebResponse.<ObatResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(
            path = "/api/obat",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public WebResponse<List<ObatResponse>> list() {
        List<ObatResponse> list = service.list();
        return WebResponse.<List<ObatResponse>>builder()
                .data(list)
                .build();
    }

    @PatchMapping(
            path = "/api/obat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ObatResponse> update(@RequestBody UpdateObatRequest request) {
        ObatResponse response = service.update(request);
        return WebResponse.<ObatResponse>builder()
                .data(response)
                .build();
    }

}
