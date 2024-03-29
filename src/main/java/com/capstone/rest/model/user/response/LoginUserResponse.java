package com.capstone.rest.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserResponse {

    private String name;

    private String email;

    private Integer role;

    private String token;

    private Long tokenExpiredAt;

}
