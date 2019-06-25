package server.request;

import server.response.Response;

@FunctionalInterface
public interface Handler {
    Response dispatch(Request request);
}