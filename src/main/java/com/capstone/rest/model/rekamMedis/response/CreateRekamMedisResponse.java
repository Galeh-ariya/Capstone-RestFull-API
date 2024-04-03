package com.capstone.rest.model.rekamMedis.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRekamMedisResponse {

    private String noRm;

    private String anamnesa;

    private String pemeriksaan;

    private String diagnosis;

    private String therapy;

    private String therapy2;

    private String therapy3;

    private String therapy4;

    private Integer jumlahObat;

    private Integer jumlahObat2;

    private Integer jumlahObat3;

    private Integer jumlahObat4;

    private LocalDate Checkin;

}
