package com.up42.api.dtos.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponseDto {

    private LoginDataDto data;
    private String error;
    @JsonProperty("access_token") // TODO check if it's really should be snake case
    private String accessToken;
    @JsonProperty("refresh_token") // TODO check if it's really should be snake case
    private String refreshToken;

}