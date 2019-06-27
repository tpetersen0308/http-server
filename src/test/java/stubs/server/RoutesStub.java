package stubs.server;

import server.RequestHandler;
import server.request.Request;
import server.response.Response;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RoutesStub {
    private RoutesStub() {
    }

    private static final RequestHandler BASIC_ROUTE_STUB = (Request request) ->
        new Response.Builder()
            .withStatus("200 OK")
            .withHeader("Content-Length", "9")
            .withHeader("Allow", "GET, HEAD")
            .withBody("test body".getBytes())
            .build();

    public static final Map<String, Map<String, RequestHandler>> ROUTES = new HashMap<String, Map<String, RequestHandler>>() {{
        put("/test-route", new LinkedHashMap<String, RequestHandler>() {{
            put("GET", BASIC_ROUTE_STUB);
            put("HEAD", BASIC_ROUTE_STUB);
        }});
    }};
}