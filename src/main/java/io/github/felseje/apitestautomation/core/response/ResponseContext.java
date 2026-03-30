package io.github.felseje.apitestautomation.core.response;

import io.github.felseje.apitestautomation.util.ArgumentValidator;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public class ResponseContext {

    private final Response response;

    public ResponseContext(Response response) {
        ArgumentValidator.requireNotNull(response, "The 'response' cannot be null");
        this.response = response;
    }

    public int statusCode() {
        return response.getStatusCode();
    }

    public ResponseContext assertStatusCode(int expectedStatusCode) {
        if (statusCode() != expectedStatusCode) {
            String errorMessage = "Status code does not match the expected value. Actual: %d, Expected: %d."
                    .formatted(statusCode(), expectedStatusCode);
            throw new AssertionError(errorMessage);
        }
        return this;
    }

    public String header(String name) {
        ArgumentValidator.requireNotBlank(name, "The header name cannot be blank");
        return response.getHeader(name);
    }

    public ResponseContext assertHeader(String name, String expectedValue) {
        String actual = header(name);
        if (!expectedValue.equals(actual)) {
            throw new AssertionError("Header '" + name + "' does not match. Actual: " + actual + ", Expected: " + expectedValue);
        }
        return this;
    }

    public <T> T as(Class<T> clazz) {
        ArgumentValidator.requireNotNull(clazz, "The 'clazz' cannot be null");
        return response.as(clazz);
    }

    public String body() {
        return response.getBody().asString();
    }

    public ResponseContext assertBodyEquals(String expectedBody) {
        ArgumentValidator.requireNotNull(expectedBody, "Expected body cannot be null");
        if (!body().equals(expectedBody)) {
            throw new AssertionError(
                    "Body does not match. Actual: " + body() + ", Expected: " + expectedBody
            );
        }
        return this;
    }

    public ResponseContext assertBodyContains(String expectedFragment) {
        ArgumentValidator.requireNotNull(expectedFragment, "Expected fragment cannot be null");
        if (!body().contains(expectedFragment)) {
            throw new AssertionError(
                    "Body does not contain expected fragment. Actual: " + body() + ", Expected fragment: " + expectedFragment
            );
        }
        return this;
    }

    public ResponseContext assertJsonSchema(String schemaJson) {
        ArgumentValidator.requireNotBlank(schemaJson, "Schema JSON cannot be blank");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaJson));
        } catch (AssertionError e) {
            throw new AssertionError("JSON Schema validation failed: " + e.getMessage(), e);
        }
        return this;
    }

    public ResponseContext assertJsonSchemaInClasspath(String classpathResource) {
        ArgumentValidator.requireNotBlank(classpathResource, "Classpath resource cannot be blank");
        try {
            var matches = JsonSchemaValidator.matchesJsonSchemaInClasspath(classpathResource);
            response.then()
                    .assertThat()
                    .body(matches);
        } catch (AssertionError e) {
            throw new AssertionError("JSON Schema validation failed for resource: " + classpathResource + ".", e);
        }
        return this;
    }

    public Response getRawResponse() {
        return response;
    }

    public <T> T path(String jsonPath) {
        ArgumentValidator.requireNotBlank(jsonPath, "The 'jsonPath' cannot be blank");
        return response.jsonPath().get(jsonPath);
    }

    public boolean jsonPathExists(String jsonPath) {
        return path(jsonPath) != null;
    }

    public boolean isSuccess() {
        return statusCode() >= 200 && statusCode() < 300;
    }

    public boolean isClientError() {
        return statusCode() >= 400 && statusCode() < 500;
    }

    public boolean isServerError() {
        return statusCode() >= 500 && statusCode() < 600;
    }

    public ResponseContext logBody() {
        response.then().log().body();
        return this;
    }

    public ResponseContext logAll() {
        response.then().log().all();
        return this;
    }

    public ResponseContext logIfError() {
        if (statusCode() < 400) {
            return this;
        }
        return logAll();
    }

}
