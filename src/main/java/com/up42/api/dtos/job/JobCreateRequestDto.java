package com.up42.api.dtos.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JobCreateRequestDto {

    @JsonProperty("sobloo-s2-l1c-aoiclipped")
    private SoblooS2L1cAoiclippedDto soblooS2L1cAoiclipped;
    private SharpeningDto sharpening;
    //TODO add more tasks here or make generic

}
