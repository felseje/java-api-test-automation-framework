package io.github.felseje.apitestautomation.factory;

import io.github.felseje.apitestautomation.config.Config;
import io.github.felseje.apitestautomation.config.ConfigurationManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecificationFactory {

    private RequestSpecificationFactory() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static RequestSpecification getStandard() {
        Config config = ConfigurationManager.getConfig();
        RestAssuredConfig restAssuredConfig = new RestAssuredConfig().httpClient(getHttpClientConfig(config));
        return new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setConfig(restAssuredConfig)
                .setRelaxedHTTPSValidation()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .log(LogDetail.HEADERS)
                .log(LogDetail.PARAMS)
                .log(LogDetail.BODY)
                .build();
    }

    private static HttpClientConfig getHttpClientConfig(Config config) {
        return new HttpClientConfig()
                .setParam("http.socket.timeout", config.httpSocketTimeout())
                .setParam("http.connect.timeout", config.httpConnectTimeout())
                .setParam("http.connection-manager.timeout", config.httpConnectionManagerTimeout());
    }

}
