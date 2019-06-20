package server;

import server.request.Request;
import server.response.Response;

@FunctionalInterface
public interface RequestHandler {
    Response dispatch(Request request);
}