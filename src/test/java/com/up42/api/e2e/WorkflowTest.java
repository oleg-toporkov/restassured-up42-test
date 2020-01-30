package com.up42.api.e2e;

import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.login.LoginResponseDto;
import com.up42.api.dtos.workflow.CreateWorkflowRequestDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class WorkflowTest {

    @Test
    void createWorkflow() {

        //@formatter:off
        LoginResponseDto responseDto =
            given()
                    .spec(BASE_REQUEST_SPEC)
                    .auth()
                    .basic(TestProperties.CONFIG.getProject().getId(),
                           TestProperties.CONFIG.getProject().getKey())
                    .contentType(ContentType.URLENC)
                    .body("grant_type=client_credentials").
            when()
                    .post(Endpoints.OAUTH_TOKEN).
            then()
                    .spec(BASE_RESPONSE_SPEC)
                    .extract()
                    .body()
                    .as(LoginResponseDto.class);

        CreateWorkflowRequestDto createWorkflowBody = CreateWorkflowRequestDto.builder()
                .name("Test Name")
                .description("Test Description")
                .build();

            given()
                    .spec(BASE_REQUEST_SPEC)
                    .auth()
                    .oauth2(responseDto.getAccessToken())
                    .contentType(ContentType.JSON)
                    .body(createWorkflowBody).
            when()
                    .post(Endpoints.WORKFLOWS, TestProperties.CONFIG.getProject().getId()).
            then()
                    .spec(BASE_RESPONSE_SPEC);
        //@formatter:on
    }

}
