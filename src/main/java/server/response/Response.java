package server.response;

import app.support.ResponseHandler;
import server.request.Request;
import server.response.stringcomponents.HTTP;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Response {
    private String status;
    private Map<String, String> headers;
    private String body;

    private Response() {
    }

    public String status() {
        return status;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public String body() {
        return body;
    }

    public static class Builder {
        private Response response;
        private String status;
        private Map<String, String> headers;
        private String body;

        public Builder(Response response) {
            this.response = new Response();
            this.status = response.status;
            this.headers = response.headers;
            this.body = response.body;
        }

        public Builder() {
            this.response = new Response();
            this.headers = new HashMap<>();
            this.body = WhiteSpace.EMPTY_BODY;
        }

        public Builder withStatus(String status) {
            this.status = status;

            return this;
        }

        public Builder withAllowHeader(Map<String, ResponseHandler> route) {
            Set<String> methodSet = route.keySet();
            String[] allowedMethods = methodSet.toArray(new String[methodSet.size()]);
            this.headers.put(HeaderFields.ALLOWED_METHODS, String.join(", ", allowedMethods));

            return this;
        }

        public Builder withContentLengthHeader(String body) {
            Integer contentLength = body.length();
            this.headers.put(HeaderFields.CONTENT_LENGTH, contentLength.toString());

            return this;
        }

        public Builder withLocationHeader(Request request, String path) {
            String host = request.headers().get(HeaderFields.HOST);
            String location = HTTP.URL_PREFIX + host + path;

            this.headers.put(HeaderFields.LOCATION, location);

            return this;
        }

        public Builder withCustomHeaders(Map<String, String> headers) {
            if(headers != null) {
                for(Map.Entry<String, String> header : headers.entrySet()) {
                    this.headers.put(header.getKey(), header.getValue());
                }
            }

            return this;
        }

        public Builder withBody(String body) {
            this.body = body;

            return this;
        }

        public Response build() {
            response.status = this.status;
            response.headers = this.headers;
            response.body = this.body;

            return response;
        }
    }
}
