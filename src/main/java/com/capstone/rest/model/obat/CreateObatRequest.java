package com.capstone.rest.model.obat;

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
public class CreateObatRequest {

    @NotBlank
    private String nameObat;

    @NotBlank
    private String expired;

    @NotNull
    private Integer minStock;

    @NotNull
    private Integer stock;

}
