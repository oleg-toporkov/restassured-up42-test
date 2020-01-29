package com.up42.api.e2e;

import com.up42.api.constants.Endpoints;
import com.up42.api.properties.TestProperties;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static com.up42.api.clients.base.BaseClient.BASE_RESPONSE_SPEC;
import static io.restassured.RestAssured.given;

public class LoginTest {

    @Test
    void successfulLogin() {
        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .auth()
                .basic(TestProperties.CONFIG.getProject().getId(),
                       TestProperties.CONFIG.getProject().getKey())
                .contentType("application/x-www-form-urlencoded")
                .body("grant_type=client_credentials").
        when()
                .post(Endpoints.OAUTH_TOKEN).
        then()
                .spec(BASE_RESPONSE_SPEC);
        //@formatter:on
    }

}
