package com.up42.api.dtos.workflow;

import lombok.Data;

@Data
public class CreateWorkflowResponseDto {

    private String error;
    private WorkflowDataDto data;

}
