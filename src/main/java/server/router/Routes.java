package server.router;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static final Map<String, Map<String, MethodApplier>> ROUTES;
    static {

        Map<String, Map<String, MethodApplier>> routes = new HashMap<String, Map<String, MethodApplier>>() {{
            put ("/simple_get", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", Methods.SIMPLE_GET);
                put("OPTIONS", Methods.SIMPLE_OPTIONS);
            }});
            put("/get_with_body", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", Methods.SIMPLE_GET);
                put("OPTIONS", Methods.SIMPLE_OPTIONS);
            }});
            put("/method_options", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", Methods.SIMPLE_GET);
                put("OPTIONS", Methods.SIMPLE_OPTIONS);
            }});
            put("/echo_body", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", Methods.SIMPLE_GET);
                put("OPTIONS", Methods.SIMPLE_OPTIONS);
                put("POST", Methods.ECHO_BODY);
            }});
        }};

        ROUTES = Collections.unmodifiableMap(routes);
    }
}
