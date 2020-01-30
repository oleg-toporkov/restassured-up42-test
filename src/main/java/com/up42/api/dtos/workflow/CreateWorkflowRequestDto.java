package com.up42.api.dtos.workflow;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateWorkflowRequestDto {

    private String name;
    private String description;

}
