package com.up42.api.clients.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.up42.api.properties.TestProperties;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

public class BaseClient {

    public static RequestSpecification BASE_REQUEST_SPEC = new RequestSpecBuilder()
            .addFilter(new AllureRestAssured())
            .setConfig(RestAssured.config().objectMapperConfig(getSnakeCaseObjectMapperConfig()))
            .log(LogDetail.ALL)
            .setBaseUri(TestProperties.CONFIG.getHost())
            .build();

    public static ResponseSpecification BASE_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_OK)
            .log(LogDetail.ALL)
            .build();


    private static ObjectMapperConfig getSnakeCaseObjectMapperConfig() {
        return new ObjectMapperConfig().jackson2ObjectMapperFactory((type, s) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            return objectMapper;
        });
    }

}
