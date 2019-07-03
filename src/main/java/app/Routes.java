package app;

import server.request.Handler;
import server.Router;
import server.request.Request;
import server.response.Renderers;

import java.util.Collections;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, Handler>> ROUTES;
    static {
        Router router = new Router();

        router.get("/simple_get", (Request request) -> Renderers.render(request, ""));
        router.options("/simple_get", (Request request) -> Renderers.render(request, ""));

        router.head("/get_with_body", (Request request) -> Renderers.render(request, "some body"));
        router.options("/get_with_body", (Request request) -> Renderers.render(request, ""));

        router.get("/method_options", (Request request) -> Renderers.render(request, ""));
        router.options("/method_options", (Request request) -> Renderers.render(request, ""));

        router.get("/method_options2", (Request request) -> Renderers.render(request, ""));
        router.options("/method_options2", (Request request) -> Renderers.render(request, ""));
        router.post("/method_options2", (Request request) -> Renderers.render(request, request.body()));
        router.put("/method_options2", (Request request) -> Renderers.render(request, request.body()));

        router.get("/echo_body", (Request request) -> Renderers.render(request, ""));
        router.options("/echo_body", (Request request) -> Renderers.render(request, ""));
        router.post("/echo_body", (Request request) -> Renderers.render(request, request.body()));

        router.get("/redirect", (Request request) -> Renderers.redirectTo(request,"/simple_get"));

        ROUTES = Collections.unmodifiableMap(router.routes());
    }
}
