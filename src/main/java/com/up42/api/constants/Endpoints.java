package com.up42.api.constants;

public interface Endpoints {
    String OAUTH_TOKEN = "/oauth/token";
    String PROJECTS = "/projects/{projectId}";
    String WORKFLOWS = String.format("%s/workflows", PROJECTS);
    String WORKFLOWS_WITH_ID = String.format("%s/{workflowId}", WORKFLOWS);
}