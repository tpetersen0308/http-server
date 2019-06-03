package server.stubs;

import app.MethodApplier;
import app.Methods;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoutesStub {
    private RoutesStub() {
    }

    public static final Map<String, Map<String, MethodApplier>> ROUTES;
    static {

        Map<String, Map<String, MethodApplier>> routes = new HashMap<String, Map<String, MethodApplier>>() {{
            put ("/simple_get", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", request -> "");
                put("OPTIONS", request -> "");
            }});
            put("/get_with_body", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", request -> "");
                put("OPTIONS", request -> "");
            }});
            put("/method_options", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", request -> "");
                put("OPTIONS", request -> "");
            }});
            put("/echo_body", new LinkedHashMap<String, MethodApplier>() {{
                put("GET", request -> "");
                put("OPTIONS", request -> "");
                put("POST", request -> request.body());
            }});
        }};

        ROUTES = Collections.unmodifiableMap(routes);
    }
}
