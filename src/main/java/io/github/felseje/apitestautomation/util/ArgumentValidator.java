package io.github.felseje.apitestautomation.util;

public final class ArgumentValidator {

    private static final String DEFAULT_NULL_MESSAGE = "Argument cannot be null";
    private static final String DEFAULT_BLANK_MESSAGE = "Argument cannot be blank";

    private ArgumentValidator() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static void requireNotNull(Object argument, String errorMessage) {
        if (argument != null) {
            return;
        }
        throw new IllegalArgumentException(
                isBlank(errorMessage) ? DEFAULT_NULL_MESSAGE : errorMessage
        );
    }

    public static void requireNotBlank(String argument, String errorMessage) {
        if (!isBlank(argument)) {
            return;
        }
        throw new IllegalArgumentException(
                isBlank(errorMessage) ? DEFAULT_BLANK_MESSAGE : errorMessage
        );
    }

    private static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

}
