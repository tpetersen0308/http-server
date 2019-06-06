package app;

import java.util.Collections;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, MethodApplier>> ROUTES;
    static {
        Router router = new Router();

        router.get("/simple_get", Methods.SIMPLE_GET);
        router.options("/simple_get", Methods.SIMPLE_OPTIONS);

        router.head("/get_with_body", Methods.GET_WITH_BODY);
        router.options("/get_with_body", Methods.SIMPLE_OPTIONS);

        router.get("/method_options", Methods.SIMPLE_GET);
        router.options("/method_options", Methods.SIMPLE_OPTIONS);

        router.get("/method_options2", Methods.SIMPLE_GET);
        router.options("/method_options2", Methods.SIMPLE_OPTIONS);
        router.post("/method_options2", Methods.ECHO_BODY);
        router.put("/method_options2", Methods.ECHO_BODY);

        router.get("/echo_body", Methods.SIMPLE_GET);
        router.options("/echo_body", Methods.SIMPLE_OPTIONS);
        router.post("/echo_body", Methods.ECHO_BODY);

        ROUTES = Collections.unmodifiableMap(router.routes);
    }
}
