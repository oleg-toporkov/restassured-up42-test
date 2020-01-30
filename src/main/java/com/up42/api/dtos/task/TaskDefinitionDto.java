package com.up42.api.dtos.task;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDefinitionDto {

    private String name;
    private String parentName;
    private String blockId;

}
