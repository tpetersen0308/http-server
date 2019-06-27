package server.request;

import server.Client;

import java.util.Map;
import java.util.HashMap;

public class RequestParser {
    Client client;

    public RequestParser(Client client) {
        this.client = client;
    }

    public Request parse() {
        String requestLine = parseRequestLine();
        String requestMethod = parseRequestMethod(requestLine);
        String requestPath = parseRequestPath(requestLine);

        Map<String, String> requestHeaders = parseRequestHeaders();
        String requestBody = parseRequestBody(requestHeaders);

        return new Request(requestMethod, requestPath, requestHeaders, requestBody);
    }

    private String parseRequestLine() {
        return client.read();
    }

    private String parseRequestMethod(String requestLine) {
        try {
            return requestLine.split(" ")[0].trim();
        } catch (NullPointerException err) {
            return "";
        }
    }

    private String parseRequestPath(String requestLine) {
        return requestLine.split(" ")[1].trim();
    }

    private Map<String, String> parseRequestHeaders() {
        Map<String, String> requestHeaders = new HashMap<>();
        String header = client.read();

        while (header != null && !header.isEmpty()) {
            String[] headerComponents = header.split(": ");
            requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
            header = client.read();
        }

        return requestHeaders;
    }

    private String parseRequestBody(Map requestHeaders) {
        int contentLength = parseRequestBodyContentLength(requestHeaders);

        return client.read(contentLength);
    }

    private int parseRequestBodyContentLength(Map<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get("Content-Length");
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
