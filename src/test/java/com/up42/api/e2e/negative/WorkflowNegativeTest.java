package com.up42.api.e2e.negative;

import com.up42.api.clients.auth.OAuthClient;
import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.workflow.CreateWorkflowRequestDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WorkflowNegativeTest {

    @Test
    void createWorkflowEmptyName() {
        String workflowDescription = String.format("createWorkflowEmptyName %s description", ThreadLocalRandom.current().nextInt());

        CreateWorkflowRequestDto createWorkflowBody = CreateWorkflowRequestDto.builder()
                .name("")
                .description(workflowDescription)
                .build();
        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .contentType(ContentType.JSON)
                .body(createWorkflowBody).
        when()
                .post(Endpoints.WORKFLOWS, TestProperties.CONFIG.getProject().getId()).
        then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error.message", is("Field \"name\" must not be empty"));
        //@formatter:on
    }

}
