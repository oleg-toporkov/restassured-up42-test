package com.up42.api.dtos.job;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SoblooS2L1cAoiclippedDto {

    private List<String> ids;
    private List<Float> bbox;
    private Integer limit;
    private Integer zoomLevel;

}