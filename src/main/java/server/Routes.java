package server;

import java.util.HashMap;
import java.util.Map;

public class Routes {
    private Routes() {
    }

    public static Map<String, Map<String, String>> ROUTES = new HashMap<String, Map<String, String>>() {{
        put("/simple_get", new HashMap<String, String>() {{
            put("methods", "GET, HEAD");
        }});
        put("/get_with_body", new HashMap<String, String>() {{
            put("methods", "GET, HEAD");
        }});
        put("/method_options", new HashMap<String, String>() {{
            put("methods", "GET, HEAD, OPTIONS");
        }});
        put("/echo_body", new HashMap<String, String>() {{
            put("methods", "GET, HEAD, OPTIONS, POST");
        }});
    }};
}
