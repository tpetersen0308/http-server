package app;

import app.support.ActionDispatcher;
import app.support.Router;

import java.util.Collections;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, ActionDispatcher>> ROUTES;
    static {
        Router router = new Router();

        router.get("/simple_get", ControllerActions.SIMPLE_GET);
        router.options("/simple_get", ControllerActions.SIMPLE_OPTIONS);

        router.head("/get_with_body", ControllerActions.GET_WITH_BODY);
        router.options("/get_with_body", ControllerActions.SIMPLE_OPTIONS);

        router.get("/method_options", ControllerActions.SIMPLE_GET);
        router.options("/method_options", ControllerActions.SIMPLE_OPTIONS);

        router.get("/method_options2", ControllerActions.SIMPLE_GET);
        router.options("/method_options2", ControllerActions.SIMPLE_OPTIONS);
        router.post("/method_options2", ControllerActions.ECHO_BODY);
        router.put("/method_options2", ControllerActions.ECHO_BODY);

        router.get("/echo_body", ControllerActions.SIMPLE_GET);
        router.options("/echo_body", ControllerActions.SIMPLE_OPTIONS);
        router.post("/echo_body", ControllerActions.ECHO_BODY);

        router.get("/redirect", ControllerActions.REDIRECT);

        ROUTES = Collections.unmodifiableMap(router.routes());
    }
}
