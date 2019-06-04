package server.response;

import app.App;
import server.request.Request;
import app.MethodApplier;
import server.response.types.NotFoundResponse;
import server.response.types.OKResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseBuilder {
    public static Response buildResponse(Request request, App app) {
        Map<String, MethodApplier> route = app.routes().get(request.path());

        if (route != null) {
            String body = getBody(route, request);
            Map<String, String> headers = buildHeaders(route, request, body.length());
            return new OKResponse(headers, body);
        } else {
            return new NotFoundResponse(Collections.<String, String>emptyMap(),"");
        }
    }

    private static Map<String, String> buildHeaders(Map<String, MethodApplier> route, Request request, Integer contentLength) {
        Map<String, String> headers = new HashMap<String, String>();
        String methods = getMethods(route);
        headers.put("Allow", methods);

        if(!request.method().equals("HEAD")){
            headers.put("Content-Length", contentLength.toString());
        }

        return headers;
    }

    private static String getMethods(Map<String, MethodApplier> route) {
        Set<String> methodSet = route.keySet();
        String[] methods = methodSet.toArray(new String[methodSet.size()]);
        return String.join(", ", methods) + ", HEAD";
    }

    private static String getBody(Map<String, MethodApplier> route, Request request) {
        if(request.method().equals("HEAD"))
            return "";
        return route.get(request.method()).apply(request);
    }
}
