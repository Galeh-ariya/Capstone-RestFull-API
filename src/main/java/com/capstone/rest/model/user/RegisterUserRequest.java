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
    @Size(max = 100)
    private String diagnosis;

    @NotBlank
    @Size(max = 100)
    private String therapy;

    @NotNull
    private Integer totalObat;

}
