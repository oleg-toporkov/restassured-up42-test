package com.up42.api.e2e;

import com.up42.api.auth.OAuthClient;
import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.login.LoginResponseDto;
import com.up42.api.dtos.workflow.CreateWorkflowRequestDto;
import com.up42.api.dtos.workflow.CreateWorkflowResponseDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class WorkflowTest {

    private ThreadLocal<String> workflowId = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<String> accessToken = ThreadLocal.withInitial(() -> null);

    @BeforeEach
    void getAccessToken() {
        String token = OAuthClient.loginAsDefaultUser();
        accessToken.set(token);
    }

    @Test
    void createWorkflow() {
        String workflowName = String.format("createWorkflow %s name", ThreadLocalRandom.current().nextInt());
        String workflowDescription = String.format("createWorkflow %s description", ThreadLocalRandom.current().nextInt());

        //@formatter:off
        CreateWorkflowRequestDto createWorkflowBody = CreateWorkflowRequestDto.builder()
                .name(workflowName)
                .description(workflowDescription)
                .build();
        CreateWorkflowResponseDto createWorkflowResponse =
            given()
                    .spec(BASE_REQUEST_SPEC)
                    .auth()
                    .oauth2(accessToken.get())
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

        workflowId.set(createWorkflowResponse.getData().getId());
    }

    @AfterEach
    void cleanupWorkflow() {
        if (workflowId.get() == null) return;

        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .auth()
                .oauth2(accessToken.get()).
        when()
                .delete(Endpoints.WORKFLOWS_WITH_ID, TestProperties.CONFIG.getProject().getId(), workflowId.get()).
        then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        //@formatter:on

    }

}
