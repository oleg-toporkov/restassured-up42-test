package com.up42.api.dtos.login;

import lombok.Data;

@Data
public class LoginResponseDto {

    private LoginDataDto data;
    private String error;
    private String accessToken;
    private String refreshToken;

}