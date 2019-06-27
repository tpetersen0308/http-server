package server.response;

import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.Status;

public class ResponseTypes {
    public static Response ok(Integer contentLength) {
        return new Response.Builder()
            .withStatus(Status.OK)
            .withHeader(HeaderFields.CONTENT_LENGTH, contentLength.toString())
            .build();
    }

    public static Response methodNotAllowed(String allowedMethods) {
        return new Response.Builder()
            .withStatus(Status.METHOD_NOT_ALLOWED)
            .withHeader(HeaderFields.ALLOWED_METHODS, allowedMethods)
            .build();
    }

    public static Response notFound() {
        return new Response.Builder()
            .withStatus(Status.NOT_FOUND)
            .build();
    }

    public static Response movedPermanently(String host, String path) {
        return new Response.Builder()
            .withStatus(Status.MOVED_PERMANENTLY)
            .withHeader(HeaderFields.LOCATION, ResponseHelpers.location(host, path))
            .build();
    }
}
