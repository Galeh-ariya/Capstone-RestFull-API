package com.capstone.rest.model.obat.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObatResponse {

    private Integer idObat;

    private String nameObat;

    private LocalDate expired;

    private Integer minStock;

    private Integer stock;

    private Integer usedTotal;

}
