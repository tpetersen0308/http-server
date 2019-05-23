package server;

import java.util.HashMap;

public class Request {
    private HashMap parsedRequest;

    public Request(HashMap parsedRequest) {
        this.parsedRequest = parsedRequest;
    }

    public String getPath() {
        String requestLine = (String)parsedRequest.get("requestLine");
        String requestPath = requestLine.split(" ")[1].trim();
        return requestPath;
    }
}
