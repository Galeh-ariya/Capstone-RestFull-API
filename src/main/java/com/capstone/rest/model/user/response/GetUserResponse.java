package com.capstone.rest.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserResponse {

    private String name;

    private String email;

    private Integer role;

    private String gender;

    private String instansi;

    private String tempat_lahir;

    private LocalDate tanggal_lahir;

    private String noRm;

    private String token;

}
