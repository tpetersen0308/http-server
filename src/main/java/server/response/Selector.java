package server.response;

import server.directory.DefaultDirectory;
import server.request.Handler;
import server.request.Request;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Selector {
    private Map<String, Map<String, Handler>> routes;

    public Selector(Map<String, Map<String, Handler>> routes) {
        this.routes = routes;
    }

    public Response selectResponse(Request request) {
        Map<String, Handler> route = getRoute(request);

        if (isBadRequest(request))
            return Types.badRequest();

        if (!isRouteFound(request))
            return selectWithoutRoute(request);

        if (!isMethodAllowed(request))
            return Types.methodNotAllowed(HeaderHelpers.allowedMethods(route));

        return Methods.fromRoute(request, route);
    }

    public Response selectResponse(IOException err) {
        return Types.internalServerError();
    }

    private Response selectWithoutRoute(Request request) {
        File directoryFile = new File(DefaultDirectory.PATH + request.path());

        if (directoryFile.exists())
            return selectFromDirectory(request, directoryFile);
        return Types.notFound();
    }

    private Response selectFromDirectory(Request request, File file) {
        if (file.isFile())
            try {
                return Methods.renderFile(request, file.getPath());
            } catch (IOException err) {
                return Types.internalServerError();
            }
        return Methods.renderDirectory(request, file.getPath());
    }

    private boolean isBadRequest(Request request) {
        return request.path().isEmpty() || request.method().isEmpty();
    }

    private boolean isRouteFound(Request request) {
        return getRoute(request) != null;
    }

    private Map<String, Handler> getRoute(Request request) {
        return routes.get(request.path());
    }

    private boolean isMethodAllowed(Request request) {
        return getRoute(request).get(request.method()) != null;
    }
}
