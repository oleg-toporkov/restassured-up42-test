package com.up42.api.dtos.workflow;

import lombok.Data;

@Data
public class CreateWorkflowResponseDto {

    public String error;
    public WorkflowDataDto data;

}
