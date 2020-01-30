package com.up42.api.functional;

import com.up42.api.clients.auth.OAuthClient;
import com.up42.api.dtos.login.LoginResponseDto;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class LoginTest {

    @Test
    void successfulLogin() {
        LoginResponseDto responseDto = OAuthClient.loginAsDefaultUser();

        assertThat(responseDto.getAccessToken(), notNullValue());
        assertThat(responseDto.getError(), nullValue());
    }

}
