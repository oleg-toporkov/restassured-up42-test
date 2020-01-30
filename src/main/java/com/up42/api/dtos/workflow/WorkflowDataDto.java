package com.up42.api.dtos.workflow;

import lombok.Data;

@Data
public class WorkflowDataDto {

    private String id;
    private String name;
    private String description;
    private String createdAt;
    private String updatedAt;
    private Integer totalProcessingTime;

}
