package io.github.felseje.apitestautomation.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Strings {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

}
