package server.response;

import server.request.Request;
import app.MethodApplier;
import server.response.types.NotFoundResponse;
import server.response.types.OKResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseBuilder {
    private Request request;
    private Map<String, MethodApplier> route;

    public ResponseBuilder(Request request, Map<String, MethodApplier> route) {
        this.request = request;
        this.route = route;
    }

    public Response build() {
        if (route != null) {
            String body = getBody();
            Integer contentLength = body.length();
            Map<String, String> headers = buildHeaders(contentLength);

            return new OKResponse(headers, isHeadRequest() ? "" : body);
        } else {
            return new NotFoundResponse();
        }
    }

    private Map<String, String> buildHeaders(Integer contentLength) {
        Map<String, String> headers = new HashMap<>();
        String methods = getMethods();
        headers.put("Allow", methods);
        headers.put("Content-Length", contentLength.toString());

        return headers;
    }

    private String getMethods() {
        Set<String> methodSet = route.keySet();
        String[] methods = methodSet.toArray(new String[methodSet.size()]);

        return String.join(", ", methods);
    }

    private String getBody() {
        String method = request.method();

        return route.get(method).apply(request);
    }

    private Boolean isHeadRequest() {
        return request.method().equals("HEAD");
    }
}
