package app;

import server.request.Handler;
import server.Router;
import server.request.Request;
import server.response.Renderers;

import java.util.Collections;
import java.util.Map;

public class AlternateRoutes {
    private AlternateRoutes() {
    }

    public static final Map<String, Map<String, Handler>> ROUTES;
    static {
        Router router = new Router();

        router.get("/image", (Request request) -> Renderers.renderFile(request, "/flycatcher.jpg"));
        router.get("/text", (Request request) -> Renderers.renderFile(request, "/file1.txt"));


        ROUTES = Collections.unmodifiableMap(router.routes());
    }
}
