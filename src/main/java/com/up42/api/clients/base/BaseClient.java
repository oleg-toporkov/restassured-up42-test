package com.up42.api.clients.base;

import com.up42.api.properties.TestProperties;
import com.up42.api.restassured.AddTokenFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.List;

public class BaseClient {

    public static RequestSpecification BASE_REQUEST_SPEC = new RequestSpecBuilder()
            .addFilters(List.of(new AllureRestAssured(), new AddTokenFilter()))
            .log(LogDetail.ALL)
            .setBaseUri(TestProperties.CONFIG.getHost())
            .build();

    public static ResponseSpecification BASE_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectStatusCode(HttpStatus.SC_OK)
            .log(LogDetail.ALL)
            .build();

}
