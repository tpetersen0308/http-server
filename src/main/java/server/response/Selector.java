package server.response;

import server.directory.DefaultDirectory;
import server.request.Handler;
import server.request.Request;
import server.response.stringcomponents.HeaderFields;

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

        return selectFromRoute(request);
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
                return Renderers.renderFile(request, file.getPath());
            } catch (IOException err) {
                return Types.internalServerError();
            }
        return Renderers.renderDirectory(request, file.getPath());
    }

    private Response selectFromRoute(Request request) {
        Response response;
        try {
            response = getRoute(request).get(request.method()).dispatch(request);
        } catch (IOException err) {
            return Types.internalServerError();
        }
        return new Response.Builder(response)
            .withHeader(HeaderFields.ALLOWED_METHODS, HeaderHelpers.allowedMethods(getRoute(request)))
            .build();
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
