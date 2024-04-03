package com.capstone.rest.controller;

import com.capstone.rest.entity.User;
import com.capstone.rest.model.PagingResponse;
import com.capstone.rest.model.WebResponse;
import com.capstone.rest.model.user.RegisterUserAdminRequest;
import com.capstone.rest.model.user.RegisterUserRequest;
import com.capstone.rest.model.user.SearchUserRequest;
import com.capstone.rest.model.user.response.CountResponse;
import com.capstone.rest.model.user.response.GetUserResponse;
import com.capstone.rest.model.user.response.RegisterUserResponse;
import com.capstone.rest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(
            path = "/api/users/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> registerAdm(@RequestBody RegisterUserAdminRequest request) {
        service.registerAdm(request);
        return WebResponse.<String>builder()
                .data("OK")
                .build();
    }

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = service.registerUser(request);
        return WebResponse.<RegisterUserResponse>builder()
                .data(response)
                .build();
    }

    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<GetUserResponse> getUserCurrent(User user) {
        GetUserResponse userCurrent = service.getUserCurrent(user);
        return WebResponse.<GetUserResponse>builder()
                .data(userCurrent)
                .build();
    }

    @GetMapping(
            path = "/api/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<GetUserResponse>> list() {
        List<GetUserResponse> list = service.getAll();
        return WebResponse.<List<GetUserResponse>>builder()
                .data(list)
                .build();
    }

    @GetMapping(
            path = "/api/users/search",
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public WebResponse<List<GetUserResponse>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "no_rm", required = false) String noRm,
            @RequestParam(value = "instansi", required = false) String instansi,
            @RequestParam(value = "page", required = false, defaultValue = "0")Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        SearchUserRequest request = SearchUserRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .noRm(noRm)
                .instansi(instansi)
                .build();

        Page<GetUserResponse> responses = service.search(request);
        return WebResponse.<List<GetUserResponse>>builder()
                .data(responses.getContent())
                .paging(PagingResponse.builder()
                        .currentPage(responses.getNumber())
                        .size(responses.getSize())
                        .totalPage(responses.getTotalPages())
                        .build())
                .build();
    }

    @GetMapping(
            path = "/api/users/total",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<CountResponse> count() {
        CountResponse countResponse = service.countTotal();
        return WebResponse.<CountResponse>builder()
                .data(countResponse)
                .build();
    }

}
