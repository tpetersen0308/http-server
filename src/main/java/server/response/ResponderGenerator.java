package server.response;

import app.ActionDispatcher;
import server.request.Request;
import server.response.stringcomponents.HTTP;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.Status;

import java.util.Map;
import java.util.Set;

public class ResponderGenerator {
    Map<String, Map<String, Responder>> routes;

    public ResponderGenerator(Map<String, Map<String, Responder>> routes) {
        this.routes = routes;
    }

    public Responder forGenericRequest(ActionDispatcher action) {
        return (Request request) -> {
            String body = action.dispatch(request);
            if (body.contains("redirect"))
                return redirect(request, body);

            Response response = forHeadRequest(action).getResponse(request);
            return new Response.Builder(response)
                .withBody(body)
                .build();
        };
    }

    public Responder forHeadRequest(ActionDispatcher action) {
        return (Request request) -> {
            String body = action.dispatch(request);
            Integer contentLength = body.length();

            return new Response.Builder()
                .withStatus(Status.OK)
                .withContentLengthHeader(contentLength.toString())
                .withAllowHeader(allowedMethodsHeader(request.path()))
                .build();
        };
    }

    private Response redirect(Request request, String body) {
        return new Response.Builder()
                .withStatus(Status.MOVED_PERMANENTLY)
                .withLocationHeader(locationHeader(request, body))
                .build();
    }

    private String allowedMethodsHeader(String path) {
        Set<String> methodSet = routes.get(path).keySet();
        String[] allowedMethods = methodSet.toArray(new String[methodSet.size()]);
        return String.join(", ", allowedMethods);
    }


    private String locationHeader(Request request, String responseBody) {
        return HTTP.URL_PREFIX + hostHeader(request) + responseBody.split(": ")[1];
    }

    private String hostHeader(Request request) {
        return request.headers().get(HeaderFields.HOST);
    }
}
