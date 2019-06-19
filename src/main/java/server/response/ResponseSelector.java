package server.response;

import app.support.App;
import app.support.DefaultDirectory;
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
        if(!isRouteFound()){
            return selectWithoutRoute();
        }

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

    private Response selectWithoutRoute() {
        File directoryFile = new File(DefaultDirectory.PATH + request.path());

        if(directoryFile.exists())
            return selectFromDirectory(directoryFile);
        return responseBuilder
            .withStatus(Status.NOT_FOUND)
            .build();
    }

    private Response selectFromDirectory(File file) {
        if(file.isFile())
            return ResponseHelpers.renderTextFile(request, file.getPath());
        return ResponseHelpers.renderDirectory(request, file.getPath());
    }
}
