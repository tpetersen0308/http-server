package app.support;

import server.request.Request;
import server.response.Response;

@FunctionalInterface
public interface ActionDispatcher {
    Response dispatch(Request request);
}