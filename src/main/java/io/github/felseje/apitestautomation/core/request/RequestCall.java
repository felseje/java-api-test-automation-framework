package io.github.felseje.apitestautomation.core.request;

import io.github.felseje.apitestautomation.core.response.ResponseContext;
import io.github.felseje.apitestautomation.util.ArgumentValidator;

import java.util.function.Supplier;

public class RequestCall {

    private final Supplier<ResponseContext> action;

    public RequestCall(Supplier<ResponseContext> action) {
        ArgumentValidator.requireNotNull(action, "The 'action' cannot be null");
        this.action = action;
    }

    public ResponseContext execute() {
        return action.get();
    }

    public RetryRequest retry() {
        return new RetryRequest(action);
    }

}
