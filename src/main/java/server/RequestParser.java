package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    public HashMap parse(BufferedReader in) {
        HashMap parsedRequest = new HashMap();

        parsedRequest.put("requestLine", parseRequestLine(in));

        HashMap<String, String> requestHeaders = parseRequestHeaders(in);
        parsedRequest.put("requestHeaders", requestHeaders);

        Integer contentLength = parseRequestBodyContentLength(requestHeaders);
        parsedRequest.put("requestBody", parseRequestBody(in, contentLength));

        return parsedRequest;
    }

    public String parseRequestLine(BufferedReader in) {
        String requestLine = null;
        try {
             requestLine = in.readLine();
        } catch(IOException err) {
            System.err.println(err);
        }
        return requestLine;
    }

    public HashMap parseRequestHeaders(BufferedReader in) {
        HashMap<String, String> requestHeaders = new HashMap<>();
        try {
            String header = in.readLine();
            while(header != null && !header.isEmpty()) {
                String[] headerComponents = header.split(": ");
                requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
                header = in.readLine();
            }
        } catch(IOException err) {
            System.err.println(err);
        }
        return requestHeaders;
    }

    public String parseRequestBody(BufferedReader in, int contentLength) {
        StringBuilder requestBody = new StringBuilder();
        int nextBodyChar;
        try {
            for(int i = 0; i < contentLength; i++) {
                nextBodyChar = in.read();
                requestBody.append((char)nextBodyChar);
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