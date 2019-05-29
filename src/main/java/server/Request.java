package server;

import java.util.Map;

public class Request {
    private String requestLine;
    private String path;
    private Map<String, String> headers;

    public Request(String requestLine, String path, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.path = path;
        this.headers = headers;
    }

    public String requestLine() {
        return requestLine;
    }

    public String path() {
        return path;
    }

    public Map<String, String> headers() {
        return headers;
    }
}
