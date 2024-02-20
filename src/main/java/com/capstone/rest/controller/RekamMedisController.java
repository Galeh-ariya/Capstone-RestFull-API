package com.capstone.rest.controller;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.rekamMedis.CreateRekamMedisRequest;
import com.capstone.rest.model.rekamMedis.response.CreateRekamMedisResponse;
import com.capstone.rest.service.RekamMedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RekamMedisController {

    @Autowired
    private RekamMedisService service;

    @PostMapping(
            path = "/api/rm/{noRm}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CreateRekamMedisResponse> create(@RequestBody CreateRekamMedisRequest request, @PathVariable("noRm") String noRm) {
        CreateRekamMedisResponse response = service.create(request, noRm);
        return WebResponse.<CreateRekamMedisResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(
            path = "/api/rm/{noRm}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<CreateRekamMedisResponse>> list(@PathVariable("noRm") String noRm) {
        List<CreateRekamMedisResponse> responses = service.listById(noRm);
        return WebResponse.<List<CreateRekamMedisResponse>>builder()
                .data(responses)
                .build();
    }

}
