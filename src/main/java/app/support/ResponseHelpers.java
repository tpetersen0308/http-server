package app.support;

import app.Routes;
import server.request.Request;
import server.response.Response;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.Status;

import java.util.Map;

public class ResponseHelpers {
    private static Map<String, Map<String, ResponseHandler>> routes = Routes.ROUTES;

    public static Response renderDirectory(Request request, String path) {
        String directoryHTML = new DirectoryIndex(path).toHTML();
        Response response = render(request, directoryHTML);

        return new Response.Builder(response)
            .withContentTypeHeader("text/html; charset=utf-8")
            .build();
    }

    public static Response render(Request request, String body, Map<String, String> headers) {
        Response result = render(request, body);

        return new Response.Builder(result)
            .withCustomHeaders(headers)
            .build();
    }

    public static Response render(Request request, String body) {
        Response response = new Response.Builder()
            .withStatus(Status.OK)
            .withContentLengthHeader(body)
            .withAllowHeader(routes.get(request.path()))
            .build();

        if (request.method().equals(HTTPMethods.HEAD))
            return response;

        return new Response
            .Builder(response)
            .withBody(body)
            .build();
    }

    public static Response redirectTo(Request request, String path) {
        return new Response.Builder()
            .withStatus(Status.MOVED_PERMANENTLY)
            .withLocationHeader(request, path)
            .build();
    }
}
