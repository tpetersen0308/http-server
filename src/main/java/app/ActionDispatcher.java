package app;

import server.request.Request;

@FunctionalInterface
public interface ActionDispatcher {
    String dispatch(Request request);
}
