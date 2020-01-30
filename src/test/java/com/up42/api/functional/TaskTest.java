package com.up42.api.functional;

import com.up42.api.clients.task.TaskClient;
import com.up42.api.clients.workflow.WorkflowClient;
import com.up42.api.dtos.task.TaskDefinitionDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class TaskTest {

    private ThreadLocal<String> workflowId = ThreadLocal.withInitial(() -> null);

    @Test
    void createTask() {
        String workflowName = String.format("createTask %s name", ThreadLocalRandom.current().nextInt());
        String workflowDescription = String.format("createTask %s description", ThreadLocalRandom.current().nextInt());

        String testWorkflowId = WorkflowClient.createWorkflow(workflowName, workflowDescription).getData().getId();
        workflowId.set(testWorkflowId);

        TaskDefinitionDto firstTask = TaskDefinitionDto.builder()
                .name(String.format("createTask %s - sobloo-s2-l1c-aoiclipped", ThreadLocalRandom.current().nextInt()))
                .blockId("3a381e6b-acb7-4cec-ae65-50798ce80e64")
                .build();

        TaskDefinitionDto secondTask = TaskDefinitionDto.builder()
                .name(String.format("createTask %s - sharpening", ThreadLocalRandom.current().nextInt()))
                .parentName(firstTask.getName())
                .blockId("e374ea64-dc3b-4500-bb4b-974260fb203e")
                .build();

        TaskClient.createTask(testWorkflowId, firstTask, secondTask);
    }


    @AfterEach
    void cleanup() {
        if (workflowId.get() != null) {
            WorkflowClient.deleteWorkflow(workflowId.get());
            workflowId.set(null);
        }
    }

}
