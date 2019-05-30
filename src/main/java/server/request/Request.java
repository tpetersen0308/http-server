package server.request;

import java.util.Map;

public class Request {
    private String path;
    private Map<String, String> headers;
    private String body;

    public Request(String path, Map<String, String> headers, String body) {
        this.path = path;
        this.headers = headers;
        this.body = body;
    }

    public String path() {
        return path;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public String body() {
        return body;
    }
}
