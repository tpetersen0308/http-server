package server.response.stringcomponents;

import java.util.HashMap;
import java.util.Map;

public class ContentTypes {
    private ContentTypes() {
    }

    private static String CHARSET = "charset=utf-8";

    public static final Map<String, String> HEADER_VALUES = new HashMap<String, String>() {{
        put("", "text/plain; " + CHARSET);
        put(".txt", "text/plain; " + CHARSET);
        put(".html", "text/html; " + CHARSET);
        put(".json", "application/json; " + CHARSET);
    }};
}
