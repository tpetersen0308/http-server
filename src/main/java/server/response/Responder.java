package server.response;

import server.request.Request;

@FunctionalInterface
public interface Responder {
    Response getResponse(Request request);
}
