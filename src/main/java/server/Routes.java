package server;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static Map<String, Map<String, String>> ROUTES = new HashMap() {{
        put("/simple_get", new HashMap() {{
            put("methods", "GET, HEAD");
        }});
        put("/get_with_body", new HashMap() {{
            put("methods", "GET, HEAD");
        }});
    }};
}
