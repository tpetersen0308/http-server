package server;

import java.util.Map;

public class Request {
    private String requestLine;
    private String path;

    public Request(String requestLine, String path, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
        this.path = path;
    }

    public String requestLine() {
        return requestLine;
    }

    public String path() {
        return path;
    }
}
