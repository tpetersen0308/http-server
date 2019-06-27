package server.response;

import server.directory.DefaultDirectory;
import server.RequestHandler;
import server.request.Request;

import java.io.File;
import java.util.Map;

public class ResponseSelector {
    private Map<String, Map<String, RequestHandler>> routes;

    public ResponseSelector(Map<String, Map<String, RequestHandler>> routes) {
        this.routes = routes;
    }

    public Response selectResponse(Request request) {
        Map<String, RequestHandler> route = getRoute(request);

        if(!isRouteFound(request)){
            return selectWithoutRoute(request);
        }

        if(!isMethodAllowed(request))
            return ResponseTypes.methodNotAllowed(ResponseHelpers.allowedMethods(route));

        return ResponseMethods.fromRoute(request, route);
    }

    private Map<String, RequestHandler> getRoute(Request request) {
        return routes.get(request.path());
    }

    private boolean isRouteFound(Request request) {
        return getRoute(request) != null;
    }

    private boolean isMethodAllowed(Request request) {
        return getRoute(request).get(request.method()) != null;
    }

    private Response selectWithoutRoute(Request request) {
        File directoryFile = new File(DefaultDirectory.PATH + request.path());

        if(directoryFile.exists())
            return selectFromDirectory(request, directoryFile);
        return ResponseTypes.notFound();
    }

    private Response selectFromDirectory(Request request, File file) {
        if(file.isFile())
            return ResponseMethods.renderFile(request, file.getPath());
        return ResponseMethods.renderDirectory(request, file.getPath());
    }
}
