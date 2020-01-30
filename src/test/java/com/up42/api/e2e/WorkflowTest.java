package com.up42.api.e2e;

import com.up42.api.clients.auth.OAuthClient;
import com.up42.api.clients.workflow.WorkflowClient;
import com.up42.api.dtos.workflow.CreateWorkflowResponseDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class WorkflowTest {

    private ThreadLocal<String> workflowId = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<String> accessToken = ThreadLocal.withInitial(() -> null);

    @BeforeEach
    void getAccessToken() {
        String token = OAuthClient.loginAsDefaultUser().getAccessToken();
        accessToken.set(token);
    }

    @ParameterizedTest
    @MethodSource("workflowNames")
    void createWorkflow(String name) {
        String workflowDescription = String.format("createWorkflow %s description", ThreadLocalRandom.current().nextInt());
        LocalDateTime now = ZonedDateTime.now(ZoneId.of("UTC")).withNano(0).withSecond(0).toLocalDateTime();

        CreateWorkflowResponseDto createWorkflowResponse =
            WorkflowClient.createWorkflow(accessToken.get(), name, workflowDescription);

        workflowId.set(createWorkflowResponse.getData().getId());

        assertThat(createWorkflowResponse.getError(), nullValue());
        assertThat(createWorkflowResponse.getData().getId(), notNullValue());
        assertThat(createWorkflowResponse.getData().getName(), is(name));
        assertThat(createWorkflowResponse.getData().getDescription(), is(workflowDescription));
        assertThat(createWorkflowResponse.getData().getCreatedAt(), containsString(now.toString())); // TODO check if it supposed to be UTC
//        assertThat(createWorkflowResponse.getData().getTotalProcessingTime(), is(greaterThan(0))); TODO should not be 0 as for me
    }

    static Stream<Arguments> workflowNames() { // TODO can be move to separate class
        return Stream.of(
            Arguments.of(String.format("createWorkflow %s name", ThreadLocalRandom.current().nextInt())),
            // TODO even if name is too long, 500 should not be thrown
            Arguments.of(String.format("createWorkflowLongName %s name", RandomStringUtils.randomAlphabetic(500)))
        );
    }

    @AfterEach
    void cleanupWorkflow() {
        if (workflowId.get() != null) {
            WorkflowClient.deleteWorkflow(accessToken.get(), workflowId.get());
            workflowId.set(null);
            accessToken.set(null);
        }
    }

}
