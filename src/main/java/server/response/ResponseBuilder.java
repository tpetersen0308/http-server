package server.response;

import server.request.Request;
import app.MethodApplier;
import server.response.stringcomponents.HTTPMethods;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;
import server.response.types.MethodNotAllowedResponse;
import server.response.types.NotFoundResponse;
import server.response.types.OKResponse;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ResponseBuilder {
    private Request request;
    private Map<String, MethodApplier> route;

    public ResponseBuilder(Request request, Map<String, MethodApplier> route) {
        this.request = request;
        this.route = route;
    }

    public Response build() {
        if (!isRouteFound())
            return new NotFoundResponse();

        if(!isMethodAllowed())
            return new MethodNotAllowedResponse(buildHeaders());

        String body = getBody();
        Integer contentLength = body.length();
        Map<String, String> headers = buildHeaders(contentLength);

        if(isHeadRequest())
            return new OKResponse(headers, WhiteSpace.EMPTY_BODY);

        return new OKResponse(headers, body);
    }

    private Map<String, String> buildHeaders(Integer contentLength) {
        Map<String, String> headers = buildHeaders();
        if(isMethodAllowed()){
            headers.put(HeaderFields.CONTENT_LENGTH, contentLength.toString());
        }

        return headers;
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        String methods = getMethods();
        headers.put(HeaderFields.ALLOWED_METHODS, methods);

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

    private Boolean isRouteFound() {
        return route != null;
    }

    private Boolean isMethodAllowed() {
        return route.get(request.method()) != null;
    }

    private Boolean isHeadRequest() {
        return request.method().equals(HTTPMethods.HEAD);
    }
}
