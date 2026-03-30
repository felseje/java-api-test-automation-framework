package io.github.felseje.apitestautomation.factory;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;

public final class ResponseSpecificationFactory {

    private ResponseSpecificationFactory() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static ResponseSpecification getStandard() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

}
