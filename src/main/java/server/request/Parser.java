package server.request;

import server.Client;
import server.response.stringcomponents.HeaderFields;
import server.response.stringcomponents.WhiteSpace;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class Parser {
    public Parser() {
    }

    public Request parse(Client client) throws IOException {
        String requestLine = parseRequestLine(client);
        String requestMethod = parseRequestMethod(requestLine);
        String requestPath = parseRequestPath(requestLine);

        Map<String, String> requestHeaders = parseRequestHeaders(client);
        String requestBody = parseRequestBody(client, requestHeaders);

        return new Request(requestMethod, requestPath, requestHeaders, requestBody);
    }

    private String parseRequestLine(Client client) throws IOException {
        String emptyRequestLine = "";
        String requestLine = client.read();
        return requestLine == null ? emptyRequestLine : requestLine;
    }

    private String parseRequestMethod(String requestLine) {
        return requestLine.split(WhiteSpace.SP)[0].trim();
    }

    private String parseRequestPath(String requestLine) {
        String emptyPath = "";
        return requestLine.isEmpty() ? emptyPath : requestLine.split(WhiteSpace.SP)[1].trim();
    }

    private Map<String, String> parseRequestHeaders(Client client) throws IOException {
        Map<String, String> requestHeaders = new HashMap<>();
        String header = client.read();

        while (header != null && !header.isEmpty()) {
            String[] headerComponents = header.split(": ");
            requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
            header = client.read();
        }

        return requestHeaders;
    }

    private String parseRequestBody(Client client, Map requestHeaders) throws IOException {
        int contentLength = parseRequestBodyContentLength(requestHeaders);

        return client.read(contentLength);
    }

    private int parseRequestBodyContentLength(Map<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get(HeaderFields.CONTENT_LENGTH);
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
