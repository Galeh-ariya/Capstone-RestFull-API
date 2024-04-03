package com.capstone.rest.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String gender;

    @NotBlank
    @Size(max = 100)
    private String tempat_lahir;

    @NotBlank
    @Size(max = 100)
    private String birthDate;

    @NotBlank
    @Size(max = 100)
    private String category;

    @NotBlank
    @Size(max = 100)
    private String instansi;

    @NotBlank
    @Size(max = 100)
    private String anamnesa;

    @NotBlank
    private String pemeriksaan;

    @NotBlank
    @Size(max = 100)
    private String diagnosis;

    @NotBlank
    @Size(max = 100)
    private String therapy;

    private String therapy2;

    private String therapy3;

    private String therapy4;

    @NotNull
    private Integer totalObat;

    private Integer totalObat2;

    private Integer totalObat3;

    private Integer totalObat4;

}
