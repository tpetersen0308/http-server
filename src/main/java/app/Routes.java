package app;

import java.util.Collections;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, MethodApplier>> ROUTES;
    static {
        Router router = new Router();

        router.addMethodToRoute("/simple_get", "GET", Methods.SIMPLE_GET);
        router.addMethodToRoute("/simple_get", "OPTIONS", Methods.SIMPLE_OPTIONS);

        router.addMethodToRoute("/get_with_body", "GET", Methods.GET_WITH_BODY);
        router.addMethodToRoute("/get_with_body", "OPTIONS", Methods.SIMPLE_OPTIONS);

        router.addMethodToRoute("/method_options", "GET", Methods.SIMPLE_GET);
        router.addMethodToRoute("/method_options", "OPTIONS", Methods.SIMPLE_OPTIONS);

        router.addMethodToRoute("/method_options2", "GET", Methods.SIMPLE_GET);
        router.addMethodToRoute("/method_options2", "OPTIONS", Methods.SIMPLE_OPTIONS);
        router.addMethodToRoute("/method_options2", "POST", Methods.ECHO_BODY);
        router.addMethodToRoute("/method_options2", "PUT", Methods.ECHO_BODY);

        router.addMethodToRoute("/echo_body", "GET", Methods.SIMPLE_GET);
        router.addMethodToRoute("/echo_body", "OPTIONS", Methods.SIMPLE_OPTIONS);
        router.addMethodToRoute("/echo_body", "POST", Methods.ECHO_BODY);

        ROUTES = Collections.unmodifiableMap(router.routes);
    }
}
