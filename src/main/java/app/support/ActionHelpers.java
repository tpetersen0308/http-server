package app.support;

import app.Routes;
import server.request.Request;
import server.response.Response;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.Status;

import java.util.HashMap;
import java.util.Map;

public class ActionHelpers {
    public static Response renderWithHeaders(Request request, String body, Map<String, String> headers) {
        Response result = render(request, body);
        return new Response.Builder(result).withCustomHeaders(headers).build();
    }

    public static Response redirectTo(Request request, String path) {
        return new Response.Builder().withStatus(Status.MOVED_PERMANENTLY).withLocationHeader(request, path).build();
    }

    public static Response render(Request request, String body) {
        Response response = new Response.Builder()
            .withStatus(Status.OK)
            .withContentLengthHeader(body)
            .withAllowHeader(Routes.ROUTES.get(request.path()))
            .build();

        if (request.method().equals(HTTPMethods.HEAD))
            return response;

        return new Response.Builder(response).withBody(body).build();
    }
}
