package server.response;

import app.support.ResponseHandler;
import server.request.Request;
import server.response.stringcomponents.ContentTypes;
import server.response.stringcomponents.HTTP;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;

import java.io.File;
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
            this.headers.put(HeaderFields.ALLOWED_METHODS, getAllowedMethods(route));

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

        public Builder withContentTypeHeader(File file) {
            this.headers.put(HeaderFields.CONTENT_TYPE, getContentType(file));

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

        private String getAllowedMethods(Map<String, ResponseHandler> route) {
            if(route == null)
                return "GET, HEAD";

            Set<String> methodSet = route.keySet();
            String[] allowedMethods = methodSet.toArray(new String[methodSet.size()]);
            return String.join(", ", allowedMethods);
        }

        private String getContentType(File file) {
            String extension = getFileExtension(file);
            String contentType = ContentTypes.HEADER_VALUES.get(extension);
            if(contentType == null)
                return ContentTypes.HEADER_VALUES.get("");
            return contentType;
        }

        private String getFileExtension(File file) {
            String name = file.getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf == -1) {
                return ""; // empty extension
            }
            return name.substring(lastIndexOf);
        }
    }
}
