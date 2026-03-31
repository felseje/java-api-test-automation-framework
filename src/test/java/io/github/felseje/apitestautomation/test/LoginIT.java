package io.github.felseje.apitestautomation.test;

import io.github.felseje.apitestautomation.client.login.LoginClient;
import io.github.felseje.apitestautomation.client.login.dto.request.LoginRequest;
import io.github.felseje.apitestautomation.core.response.ApiResponse;
import io.github.felseje.apitestautomation.service.login.LoginService;
import io.github.felseje.apitestautomation.testdata.LoginProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Tag("login")
@DisplayName("Login Tests")
public class LoginIT {

    private static final String LOGIN_SUCCESS_SCHEMA = "schemas/login/success.json";
    private static final String LOGIN_UNAUTHORIZED_SCHEMA = "schemas/login/unauthorized.json";
    private static final String LOGIN_BLANK_EMAIL_SCHEMA = "schemas/login/blank-email.json";
    private static final String LOGIN_BLANK_PASSWORD_SCHEMA = "schemas/login/blank-password.json";
    private final LoginService loginService = new LoginService(new LoginClient());

    static Stream<Arguments> provideBlankLoginFieldsForBadRequest() {
        return Stream.of(
                Arguments.of(
                        "Should return Bad Request when email is blank",
                        LOGIN_BLANK_EMAIL_SCHEMA,
                        LoginProvider.getLoginRequestWithBlankEmail()
                ),
                Arguments.of(
                        "Should return Bad Request when password is blank",
                        LOGIN_BLANK_PASSWORD_SCHEMA,
                        LoginProvider.getLoginRequestWithBlankPassword()
                )
        );
    }

    @Test
    @DisplayName("Should authenticate successfully when valid credentials are provided")
    void shouldAuthenticateSuccessfully() {
        // Arrange
        LoginRequest request = LoginProvider.getValidLoginRequest();

        // Act
        ApiResponse response = loginService.login(request);

        // Assert
        response.shouldBeOk()
                .shouldMatchJsonSchemaInClasspath(LOGIN_SUCCESS_SCHEMA);
    }

    @Test
    @DisplayName("Should return unauthorized when invalid credentials are provided")
    void shouldReturnUnauthorizedWhenCredentialsAreInvalid() {
        // Arrange
        LoginRequest request = LoginProvider.getLoginRequestWithUnauthorizedCredentials();

        // Act
        ApiResponse response = loginService.login(request);

        // Assert
        response.shouldBeUnauthorized()
                .shouldMatchJsonSchemaInClasspath(LOGIN_UNAUTHORIZED_SCHEMA);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("provideBlankLoginFieldsForBadRequest")
    void shouldReturnBadRequestForBlankFields(String testName, String expectedSchema, LoginRequest request) {
        // Act
        ApiResponse response = loginService.login(request);

        // Assert
        response.shouldBeBadRequest()
                .shouldMatchJsonSchemaInClasspath(expectedSchema);
    }

}
