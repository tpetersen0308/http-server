package server;

import java.util.Map;

public class Request {
    private Map parsedRequest;

    public Request(Map parsedRequest) {
        this.parsedRequest = parsedRequest;
    }

    public String getPath() {
        String requestLine = (String) parsedRequest.get("requestLine");
        String requestPath = requestLine.split(" ")[1].trim();
        return requestPath;
    }
}
