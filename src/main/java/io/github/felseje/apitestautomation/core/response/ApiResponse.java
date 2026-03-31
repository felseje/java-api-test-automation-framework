package io.github.felseje.apitestautomation.core.response;

import io.github.felseje.apitestautomation.util.ArgumentValidator;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@RequiredArgsConstructor
public class ApiResponse {

    private static final int SC_TOO_MANY_REQUESTS = 429;
    private final Response response;

    public ApiResponse shouldHaveStatus(int expectedStatusCode) {
        response.then()
                .assertThat()
                .statusCode(expectedStatusCode);
        return this;
    }

    public ApiResponse shouldBeOk() {
        return shouldHaveStatus(SC_OK);
    }

    public ApiResponse shouldBeCreated() {
        return shouldHaveStatus(SC_CREATED);
    }

    public ApiResponse shouldBeAccepted() {
        return shouldHaveStatus(SC_ACCEPTED);
    }

    public ApiResponse shouldBeNoContent() {
        return shouldHaveStatus(SC_NO_CONTENT);
    }

    public ApiResponse shouldBeBadRequest() {
        return shouldHaveStatus(SC_BAD_REQUEST);
    }

    public ApiResponse shouldBeUnauthorized() {
        return shouldHaveStatus(SC_UNAUTHORIZED);
    }

    public ApiResponse shouldBeForbidden() {
        return shouldHaveStatus(SC_FORBIDDEN);
    }

    public ApiResponse shouldBeNotFound() {
        return shouldHaveStatus(SC_NOT_FOUND);
    }

    public ApiResponse shouldBeUnprocessableEntity() {
        return shouldHaveStatus(SC_UNPROCESSABLE_ENTITY);
    }

    public ApiResponse shouldBeTooManyRequests() {
        return shouldHaveStatus(SC_TOO_MANY_REQUESTS);
    }

    public ApiResponse shouldBeInternalServerError() {
        return shouldHaveStatus(SC_INTERNAL_SERVER_ERROR);
    }

    public ApiResponse shouldBeBadGateway() {
        return shouldHaveStatus(SC_BAD_GATEWAY);
    }

    public ApiResponse shouldBeServiceUnavailable() {
        return shouldHaveStatus(SC_SERVICE_UNAVAILABLE);
    }

    public ApiResponse shouldBeGatewayTimeout() {
        return shouldHaveStatus(SC_GATEWAY_TIMEOUT);
    }

    public ApiResponse shouldHaveJsonField(String path, Object value) {
        ArgumentValidator.requireNotBlank(path, "Json path cannot be blank");
        response.then()
                .assertThat()
                .body(path, equalTo(value));
        return this;
    }

    public ApiResponse shouldHaveHeader(String headerName, String expectedValue) {
        ArgumentValidator.requireNotBlank(headerName, "Header name cannot be blank");
        ArgumentValidator.requireNotBlank(expectedValue, "Expected header value cannot be blank");
        response.then()
                .assertThat()
                .header(headerName, equalTo(expectedValue));
        return this;
    }

    public ApiResponse shouldHaveHeaderContaining(String headerName, String expectedSubstring) {
        ArgumentValidator.requireNotBlank(headerName, "Header name cannot be blank");
        ArgumentValidator.requireNotBlank(expectedSubstring, "Expected substring cannot be blank");
        response.then()
                .assertThat()
                .header(headerName, containsString(expectedSubstring));
        return this;
    }

    public ApiResponse shouldHaveHeaderMatching(String headerName, String regex) {
        ArgumentValidator.requireNotBlank(headerName, "Header name cannot be blank");
        ArgumentValidator.requireNotBlank(regex, "Regex cannot be blank");
        response.then()
                .assertThat()
                .header(headerName, matchesPattern(regex));
        return this;
    }

    public ApiResponse shouldHaveXmlField(String xpath, String value) {
        ArgumentValidator.requireNotBlank(xpath, "Xml path cannot be blank");
        response.then()
                .assertThat()
                .body(hasXPath(xpath, equalTo(value)));
        return this;
    }

    public ApiResponse shouldMatchJsonSchema(String schemaJson) {
        ArgumentValidator.requireNotBlank(schemaJson, "Schema JSON cannot be blank");
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schemaJson));
        return this;
    }

    public ApiResponse shouldMatchJsonSchemaInClasspath(String classpathResource) {
        ArgumentValidator.requireNotBlank(classpathResource, "Classpath resource cannot be blank");
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(classpathResource));
        return this;
    }

    public Response raw() {
        return response;
    }

    public <T> T as(Class<T> clazz) {
        return response.as(clazz);
    }

    public <T> List<T> asList(Class<T> clazz) {
        return response.as(new TypeRef<List<T>>() {
        });
    }

}
