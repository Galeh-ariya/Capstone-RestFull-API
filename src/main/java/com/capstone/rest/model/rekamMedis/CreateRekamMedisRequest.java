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
    private String diagnosis;

    @NotBlank
    private String therapy;

    @NotNull
    private Integer jumlahObat;

}
