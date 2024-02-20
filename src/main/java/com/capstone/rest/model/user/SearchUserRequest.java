package com.capstone.rest.model.user;

import jakarta.persistence.criteria.CriteriaBuilder;
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
public class SearchUserRequest {


    private String name;

    private String noRm;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;


}
