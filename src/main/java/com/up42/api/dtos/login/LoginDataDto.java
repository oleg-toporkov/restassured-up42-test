package com.up42.api.dtos.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDataDto {

    @JsonProperty("accessToken") // TODO check if it's really should be so as rest of the fields are snake case
    private String accessToken;

}