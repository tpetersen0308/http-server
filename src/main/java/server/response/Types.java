package server.response;

import server.response.stringcomponents.ContentTypes;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.Status;

public class Types {
    public static Response ok(Integer contentLength) {
        return new Response.Builder()
            .withStatus(Status.OK)
            .withHeader(HeaderFields.CONTENT_LENGTH, contentLength.toString())
            .withHeader(HeaderFields.CONTENT_TYPE, ContentTypes.HEADER_VALUES.get(""))
            .build();
    }

    public static Response movedPermanently(String host, String path) {
        return new Response.Builder()
            .withStatus(Status.MOVED_PERMANENTLY)
            .withHeader(HeaderFields.LOCATION, HeaderHelpers.location(host, path))
            .build();
    }

    public static Response badRequest() {
        return new Response.Builder()
            .withStatus(Status.BAD_REQUEST)
            .build();
    }

    public static Response notFound() {
        return new Response.Builder()
            .withStatus(Status.NOT_FOUND)
            .build();
    }

    public static Response methodNotAllowed(String allowedMethods) {
        return new Response.Builder()
            .withStatus(Status.METHOD_NOT_ALLOWED)
            .withHeader(HeaderFields.ALLOWED_METHODS, allowedMethods)
            .build();
    }

    public static Response internalServerError() {
        return new Response.Builder()
            .withStatus(Status.SERVER_ERROR)
            .build();
    }
}
