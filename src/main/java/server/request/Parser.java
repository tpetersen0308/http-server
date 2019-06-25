package server.request;

import server.Client;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Parser {
    Client client;

    public Parser(Client client) {
        this.client = client;
    }

    public Request parse() throws IOException {
        String requestLine = parseRequestLine();
        String requestMethod = parseRequestMethod(requestLine);
        String requestPath = parseRequestPath(requestLine);

        Map<String, String> requestHeaders = parseRequestHeaders();
        String requestBody = parseRequestBody(requestHeaders);

        return new Request(requestMethod, requestPath, requestHeaders, requestBody);
    }

    private String parseRequestLine() throws IOException {
        String requestLine = client.read();
        return requestLine == null ? "" : requestLine;
    }

    private String parseRequestMethod(String requestLine) {
        return requestLine.split(WhiteSpace.SP)[0].trim();
    }

    private String parseRequestPath(String requestLine) {
        return requestLine.isEmpty() ? "" : requestLine.split(WhiteSpace.SP)[1].trim();
    }

    private Map<String, String> parseRequestHeaders() throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        String header = client.read();

        while (header != null && !header.isEmpty()) {
            String[] headerComponents = header.split(": ");
            requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
            header = client.read();
        }

        return requestHeaders;
    }

    private String parseRequestBody(Map requestHeaders) throws IOException {
        int contentLength = parseRequestBodyContentLength(requestHeaders);

        return client.read(contentLength);
    }

    private int parseRequestBodyContentLength(Map<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get(HeaderFields.CONTENT_LENGTH);
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
