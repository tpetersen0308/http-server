package server.response;

import java.util.Collections;
import java.util.Map;

public class Response {
    protected String status;
    protected Map<String, String> headers;
    protected String body;

    public Response(Map<String, String> headers, String body) {
        this.status = "";
        this.headers = headers;
        this.body = body;
    }

    public Response(Map<String, String> headers) {
        this.headers = headers;
        this.body = "";
    }

    public Response() {
        this.headers = Collections.emptyMap();
        this.body = "";
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
}
