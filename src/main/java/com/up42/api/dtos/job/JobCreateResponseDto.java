package com.up42.api.dtos.job;

import lombok.Data;

@Data
public class JobCreateResponseDto {

    private String error;
    private JobDataDto data;

}
