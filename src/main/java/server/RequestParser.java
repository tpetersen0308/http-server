package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    private BufferedReader in;

    public RequestParser(BufferedReader in) {
        this.in = in;
    }

    public HashMap parse() {
        HashMap parsedRequest = new HashMap();

        parsedRequest.put("requestLine", parseRequestLine());

        HashMap<String, String> requestHeaders = parseRequestHeaders();
        parsedRequest.put("requestHeaders", requestHeaders);

        Integer contentLength = parseRequestBodyContentLength(requestHeaders);
        parsedRequest.put("requestBody", parseRequestBody(contentLength));

        return parsedRequest;
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

    private HashMap parseRequestHeaders() {
        HashMap<String, String> requestHeaders = new HashMap<>();
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

    private String parseRequestBody(int contentLength) {
        StringBuilder requestBody = new StringBuilder();
        int nextBodyChar;
        try {
            for (int i = 0; i < contentLength; i++) {
                nextBodyChar = in.read();
                requestBody.append((char) nextBodyChar);
            }
        } catch (IOException err) {
            System.err.println(err);
        }

        return requestBody.toString();
    }

    private Integer parseRequestBodyContentLength(HashMap<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get("Content-Length");
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
