package com.up42.api.dtos.job;

import lombok.Data;

@Data
public class JobDataDto {

    private String id;
    private String displayId;
    private String createdAt;
    private String updatedAt;
    private String status;
    private String name;
    private String startedAt;
    private String finishedAt;
    private Object inputs;     //TODO deserialize more if needed
    private String mode;
    private String workflowId;
    private String workflowName;

}
