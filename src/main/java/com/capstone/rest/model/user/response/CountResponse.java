package com.capstone.rest.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountResponse {

    private Integer totalAll;

    private Integer totalUserK;

    private Integer totalUserM;

}
