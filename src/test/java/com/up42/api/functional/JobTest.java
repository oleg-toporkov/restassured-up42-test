package com.up42.api.functional;

import com.up42.api.clients.job.JobClient;
import com.up42.api.clients.task.TaskClient;
import com.up42.api.clients.workflow.WorkflowClient;
import com.up42.api.dtos.job.JobCreateRequestDto;
import com.up42.api.dtos.job.JobCreateResponseDto;
import com.up42.api.dtos.job.SharpeningDto;
import com.up42.api.dtos.job.SoblooS2L1cAoiclippedDto;
import com.up42.api.dtos.task.TaskDefinitionDto;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class JobTest {

    private ThreadLocal<String> workflowId = ThreadLocal.withInitial(() -> null);

    @Test
    void createJob() {
        String workflowName = String.format("createJob %s name", ThreadLocalRandom.current().nextInt());
        String workflowDescription = String.format("createJob %s description", ThreadLocalRandom.current().nextInt());

        String testWorkflowId = WorkflowClient.createWorkflow(workflowName, workflowDescription).getData().getId();
        workflowId.set(testWorkflowId);

        TaskDefinitionDto firstTask = TaskDefinitionDto.builder()
                .name("sobloo-s2-l1c-aoiclipped")
                .blockId("3a381e6b-acb7-4cec-ae65-50798ce80e64")
                .build();

        TaskDefinitionDto secondTask = TaskDefinitionDto.builder()
                .name("sharpening")
                .parentName(firstTask.getName())
                .blockId("e374ea64-dc3b-4500-bb4b-974260fb203e")
                .build();

        TaskClient.createTask(testWorkflowId, firstTask, secondTask);

        SharpeningDto sharpeningDto = SharpeningDto.builder()
                .strength("medium")
                .build();
        SoblooS2L1cAoiclippedDto soblooS2L1cAoiclippedDto = SoblooS2L1cAoiclippedDto.builder()
                .bbox(List.of(-5.369294f, 36.104358f, -5.33309f, 36.165145f))
                .ids(List.of("S2A_MSIL1C_20190820T110621_N0208_R137_T30STF_20190820T132731"))
                .limit(1)
                .zoomLevel(14)
                .build();
        JobCreateRequestDto jobCreateRequestDto = JobCreateRequestDto.builder() //TODO better to use dry run
                .sharpening(sharpeningDto)
                .soblooS2L1cAoiclipped(soblooS2L1cAoiclippedDto)
                .build();

        JobCreateResponseDto jobCreateResponseDto = JobClient.createJob(testWorkflowId, jobCreateRequestDto);
        assertThat(jobCreateResponseDto.getError(), nullValue());
        assertThat(jobCreateResponseDto.getData().getId(), notNullValue());
        assertThat(jobCreateResponseDto.getData().getInputs().toString(), containsString(firstTask.getName()));
        assertThat(jobCreateResponseDto.getData().getInputs().toString(), containsString(secondTask.getName()));
        assertThat(jobCreateResponseDto.getData().getStatus(), is("NOT_STARTED"));
    }

    @Test
    void jobFinished() {
        String workflowName = String.format("createJob %s name", ThreadLocalRandom.current().nextInt());
        String workflowDescription = String.format("createJob %s description", ThreadLocalRandom.current().nextInt());

        String testWorkflowId = WorkflowClient.createWorkflow(workflowName, workflowDescription).getData().getId();
        workflowId.set(testWorkflowId);

        TaskDefinitionDto firstTask = TaskDefinitionDto.builder()
                .name("sobloo-s2-l1c-aoiclipped")
                .blockId("3a381e6b-acb7-4cec-ae65-50798ce80e64")
                .build();

        TaskDefinitionDto secondTask = TaskDefinitionDto.builder()
                .name("sharpening")
                .parentName(firstTask.getName())
                .blockId("e374ea64-dc3b-4500-bb4b-974260fb203e")
                .build();

        TaskClient.createTask(testWorkflowId, firstTask, secondTask);

        SharpeningDto sharpeningDto = SharpeningDto.builder()
                .strength("medium")
                .build();
        SoblooS2L1cAoiclippedDto soblooS2L1cAoiclippedDto = SoblooS2L1cAoiclippedDto.builder()
                .bbox(List.of(-5.369294f, 36.104358f, -5.33309f, 36.165145f))
                .ids(List.of("S2A_MSIL1C_20190820T110621_N0208_R137_T30STF_20190820T132731"))
                .limit(1)
                .zoomLevel(14)
                .build();
        JobCreateRequestDto jobCreateRequestDto = JobCreateRequestDto.builder() //TODO better to use dry run
                .sharpening(sharpeningDto)
                .soblooS2L1cAoiclipped(soblooS2L1cAoiclippedDto)
                .build();

        JobCreateResponseDto jobCreateResponseDto = JobClient.createJob(testWorkflowId, jobCreateRequestDto);
        String jobId = jobCreateResponseDto.getData().getId();

        Awaitility.await()
                .atMost(Durations.FIVE_MINUTES)
                .pollInSameThread()
                .pollInterval(Durations.FIVE_SECONDS)
                .untilAsserted(() -> {
                    JobCreateResponseDto response = JobClient.getJob(jobId);

                    assertThat(response.getError(), nullValue());
                    assertThat(response.getData().getId(), is(jobId));
                    assertThat(response.getData().getInputs().toString(), containsString(firstTask.getName()));
                    assertThat(response.getData().getInputs().toString(), containsString(secondTask.getName()));
                    assertThat(response.getData().getStatus(), is("SUCCEEDED"));
                });
    }


    @AfterEach
    void cleanup() {
        if (workflowId.get() != null) {
            WorkflowClient.deleteWorkflow(workflowId.get());
            workflowId.set(null);
        }
    }

}
