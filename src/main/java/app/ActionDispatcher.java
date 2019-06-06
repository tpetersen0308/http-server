package app;

import server.request.Request;

public interface ActionDispatcher {
    String dispatch(Request request);
}
