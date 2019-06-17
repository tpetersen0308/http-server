package server.response;

import app.support.App;
import app.support.Directory;
import app.support.ResponseHandler;
import app.support.ResponseHelpers;
import server.request.Request;
import server.response.stringcomponents.Status;

import java.io.File;
import java.util.Map;

public class ResponseSelector {
    private Request request;
    private Map<String, ResponseHandler> route;
    private Response.Builder responseBuilder;

    public ResponseSelector(Request request, App app) {
        this.request = request;
        this.route = app.routes().get(request.path());
        this.responseBuilder = new Response.Builder();
    }

    public Response selectResponse() {
        File directoryFile = new File(Directory.PATH + request.path());

        if(directoryFile.exists())
            return ResponseHelpers.renderDirectory(request, directoryFile.getPath());

        if(!isRouteFound())
            return responseBuilder
                .withStatus(Status.NOT_FOUND)
                .build();

        if(!isMethodAllowed())
            return responseBuilder
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
