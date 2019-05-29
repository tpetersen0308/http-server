package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class RequestParser {
    private BufferedReader in;

    public RequestParser(BufferedReader in) {
        this.in = in;
    }

    public Request parse() {
        String requestLine = parseRequestLine();
        String requestPath = requestLine.split(" ")[1].trim();
        Map<String, String> requestHeaders = parseRequestHeaders();
        String requestBody = parseRequestBody(requestHeaders);

        return new Request(requestLine, requestPath, requestHeaders, requestBody);
    }

    private String parseRequestLine() {
        String requestLine = null;
        try {
            requestLine = in.readLine();
        } catch (IOException err) {
            System.err.println(err);
        }
        return requestLine;
    }

    private Map parseRequestHeaders() {
        Map<String, String> requestHeaders = new HashMap<>();
        try {
            String header = in.readLine();
            while (header != null && !header.isEmpty()) {
                String[] headerComponents = header.split(": ");
                requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
                header = in.readLine();
            }
        } catch (IOException err) {
            System.err.println(err);
        }
        return requestHeaders;
    }

    private String parseRequestBody(Map requestHeaders) {
        Integer contentLength = parseRequestBodyContentLength(requestHeaders);
        char[] requestBody = new char[contentLength];

        try {
            in.read(requestBody, 0, contentLength);
        } catch (IOException err) {
            System.err.println(err);
        }
        return new String(requestBody);
    }

    private Integer parseRequestBodyContentLength(Map<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get("Content-Length");
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
