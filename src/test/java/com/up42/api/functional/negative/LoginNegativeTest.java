package com.up42.api.functional.negative;

import com.up42.api.clients.auth.OAuthClient;
import com.up42.api.constants.Endpoints;
import com.up42.api.properties.TestProperties;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.up42.api.clients.base.BaseClient.BASE_REQUEST_SPEC;
import static io.restassured.RestAssured.given;

public class LoginNegativeTest {

    @Test
    void incorrectProjectId() {
        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .auth()
                .basic("not-even-a-uuid",
                        TestProperties.CONFIG.getProject().getKey())
                .contentType(ContentType.URLENC)
                .body(OAuthClient.DEFAULT_GRANT_TYPE).
        when()
                .post(Endpoints.OAUTH_TOKEN).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
        //@formatter:on
    }

    @Test
    void incorrectProjectKey() {
        //@formatter:off
        given()
                .spec(BASE_REQUEST_SPEC)
                .auth()
                .basic(TestProperties.CONFIG.getProject().getId(),
                        "most-probably-valid-key")
                .contentType(ContentType.URLENC)
                .body(OAuthClient.DEFAULT_GRANT_TYPE).
        when()
                .post(Endpoints.OAUTH_TOKEN).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
        //@formatter:on
    }

}
