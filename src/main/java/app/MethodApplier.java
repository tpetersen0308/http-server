package app;

import server.request.Request;

public interface MethodApplier {
    String apply(Request request);
}
