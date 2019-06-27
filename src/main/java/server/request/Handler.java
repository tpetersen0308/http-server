package server.request;

import server.response.Response;

import java.io.IOException;

@FunctionalInterface
public interface Handler {
    Response dispatch(Request request) throws IOException;
}