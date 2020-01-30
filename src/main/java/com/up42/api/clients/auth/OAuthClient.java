package com.up42.api.clients.auth;

import com.up42.api.constants.Endpoints;
import com.up42.api.dtos.login.LoginResponseDto;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class OAuthClient {

    public static final String DEFAULT_GRANT_TYPE = "grant_type=client_credentials";

    public static LoginResponseDto loginAsDefaultUser() {
        //@formatter:off
        return given()
                    .spec(BASE_REQUEST_SPEC)
                    .auth()
                    .basic(TestProperties.CONFIG.getProject().getId(),
                           TestProperties.CONFIG.getProject().getKey())
                    .contentType(ContentType.URLENC)
                    .body(DEFAULT_GRANT_TYPE).
               when()
                    .post(Endpoints.OAUTH_TOKEN).
               then()
                    .spec(BASE_RESPONSE_SPEC)
                    .extract()
                    .body()
                    .as(LoginResponseDto.class);
        //@formatter:on
    }

}
