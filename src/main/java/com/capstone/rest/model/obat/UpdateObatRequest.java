package com.capstone.rest.model.obat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UpdateObatRequest {

    @NotNull
    private Integer idObat;

    @NotBlank
    private String nameObat;

    @NotBlank
    private String Expired;

    private Integer minStock;

    private Integer stock;

}
