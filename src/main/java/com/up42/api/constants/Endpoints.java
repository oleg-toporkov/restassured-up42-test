package com.up42.api.constants;

public interface Endpoints {
    String OAUTH_TOKEN          = "/oauth/token";
    String PROJECTS             = "/projects/{projectId}";
    String WORKFLOWS            = String.format("%s/workflows", PROJECTS);
    String WORKFLOWS_WITH_ID    = String.format("%s/{workflowId}", WORKFLOWS);
    String TASKS                = String.format("%s/tasks", WORKFLOWS_WITH_ID);
    String CREATE_JOBS          = String.format("%s/jobs", WORKFLOWS_WITH_ID);
    String GET_JOB              = String.format("%s/jobs/{jobId}", PROJECTS);
}