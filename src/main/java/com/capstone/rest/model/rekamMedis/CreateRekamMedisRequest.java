package com.capstone.rest.model.rekamMedis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRekamMedisRequest {

    @NotBlank
    private String anamnesa;

    @NotBlank
    private String pemeriksaan;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String therapy;

    private String therapy2;

    private String therapy3;

    private String therapy4;

    @NotNull
    private Integer jumlahObat;

    private Integer jumlahObat2;

    private Integer jumlahObat3;

    private Integer jumlahObat4;

}
