package com.up42.api.clients.task;

import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.task.TaskDefinitionDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;

import java.util.List;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class TaskClient {

    public static void createTask(String workflowId, TaskDefinitionDto... taskDefinitions) {

        //@formatter:off
         given()
                        .spec(BASE_REQUEST_SPEC)
                        .contentType(ContentType.JSON)
                        .body(List.of(taskDefinitions)).
                when()
                        .post(Endpoints.TASKS, TestProperties.CONFIG.getProject().getId(), workflowId).
                then()
                        .spec(BASE_RESPONSE_SPEC);
        //@formatter:on
    }

}
