package server.response;

import app.support.App;
import app.support.ActionDispatcher;
import server.request.Request;
import server.response.stringcomponents.Status;

import java.util.Map;

public class ResponseSelector {
    private Request request;
    private Map<String, ActionDispatcher> route;

    public ResponseSelector(Request request, App app) {
        this.request = request;
        this.route = app.routes().get(request.path());
    }

    public Response selectResponse() {
        if (!isRouteFound())
            return new Response.Builder()
                .withStatus(Status.NOT_FOUND)
                .build();

        if(!isMethodAllowed())
            return new Response.Builder()
                .withStatus(Status.METHOD_NOT_ALLOWED)
                .withAllowHeader(route)
                .build();

        return route.get(request.method()).dispatch(request);
    }

    private Boolean isRouteFound() {
        return route != null;
    }

    private Boolean isMethodAllowed() {
        return route.get(request.method()) != null;
    }
}
