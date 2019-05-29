package server;

import java.util.Map;

public class Request {
    private String requestLine;

    public Request(String requestLine, Map<String, String> headers, String body) {
        this.requestLine = requestLine;
    }

    public String requestLine() {
        return requestLine;
    }

    public String getPath() {
        return requestLine.split(" ")[1].trim();
    }
}
