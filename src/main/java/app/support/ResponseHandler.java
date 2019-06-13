package app.support;

import server.request.Request;
import server.response.Response;

@FunctionalInterface
public interface ResponseHandler {
    Response dispatch(Request request);
}