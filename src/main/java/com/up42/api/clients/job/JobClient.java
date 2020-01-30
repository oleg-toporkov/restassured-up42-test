package com.up42.api.clients.job;

import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.job.JobCreateRequestDto;
import com.up42.api.dtos.job.JobCreateResponseDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class JobClient {

    public static JobCreateResponseDto createJob(String workflowId, JobCreateRequestDto jobCreateRequestDto) {
        //@formatter:off
        return given()
                        .spec(BASE_REQUEST_SPEC)
                        .contentType(ContentType.JSON)
                        .body(jobCreateRequestDto).
                when()
                        .post(Endpoints.CREATE_JOBS, TestProperties.CONFIG.getProject().getId(), workflowId).
                then()
                        .spec(BASE_RESPONSE_SPEC)
                        .extract()
                        .body()
                        .as(JobCreateResponseDto.class);
        //@formatter:on
    }

    public static JobCreateResponseDto getJob(String jobId) {
        //@formatter:off
        return given()
                        .spec(BASE_REQUEST_SPEC).
                when()
                        .get(Endpoints.GET_JOB, TestProperties.CONFIG.getProject().getId(), jobId).
                then()
                        .spec(BASE_RESPONSE_SPEC)
                        .extract()
                        .body()
                        .as(JobCreateResponseDto.class);
        //@formatter:on
    }

}
