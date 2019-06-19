package app;

import app.support.ResponseHandler;
import app.support.Router;

import java.util.Collections;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, ResponseHandler>> ROUTES;
    static {
        Router router = new Router();

        router.get("/simple_get", ResponseHandlers.SIMPLE_GET);
        router.options("/simple_get", ResponseHandlers.SIMPLE_OPTIONS);

        router.head("/get_with_body", ResponseHandlers.GET_WITH_BODY);
        router.options("/get_with_body", ResponseHandlers.SIMPLE_OPTIONS);

        router.get("/method_options", ResponseHandlers.SIMPLE_GET);
        router.options("/method_options", ResponseHandlers.SIMPLE_OPTIONS);

        router.get("/method_options2", ResponseHandlers.SIMPLE_GET);
        router.options("/method_options2", ResponseHandlers.SIMPLE_OPTIONS);
        router.post("/method_options2", ResponseHandlers.ECHO_BODY);
        router.put("/method_options2", ResponseHandlers.ECHO_BODY);

        router.get("/echo_body", ResponseHandlers.SIMPLE_GET);
        router.options("/echo_body", ResponseHandlers.SIMPLE_OPTIONS);
        router.post("/echo_body", ResponseHandlers.ECHO_BODY);

        router.get("/redirect", ResponseHandlers.REDIRECT);

        ROUTES = Collections.unmodifiableMap(router.routes());
    }
}
