package com.up42.api.clients.workflow;

import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.workflow.CreateWorkflowRequestDto;
import com.up42.api.dtos.workflow.CreateWorkflowResponseDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class WorkflowClient {

    public static CreateWorkflowResponseDto createWorkflow(String accessToken, String name, String description) {
        CreateWorkflowRequestDto createWorkflowBody = CreateWorkflowRequestDto.builder()
                .name(name)
                .description(description)
                .build();

        //@formatter:off
        return given()
                        .spec(BASE_REQUEST_SPEC)
                        .auth()
                        .oauth2(accessToken)
                        .contentType(ContentType.JSON)
                        .body(createWorkflowBody).
                when()
                        .post(Endpoints.WORKFLOWS, TestProperties.CONFIG.getProject().getId()).
                then()
                        .spec(BASE_RESPONSE_SPEC)
                        .extract()
                        .body()
                        .as(CreateWorkflowResponseDto.class);
        //@formatter:on
    }

    public static void deleteWorkflow(String accessToken, String workflowId) {
        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .auth()
                .oauth2(accessToken).
        when()
                .delete(Endpoints.WORKFLOWS_WITH_ID, TestProperties.CONFIG.getProject().getId(), workflowId).
        then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        //@formatter:on
    }

}
