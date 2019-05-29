package server.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class RequestParser {

    public static Request parse(BufferedReader in) {
        String requestLine = parseRequestLine(in);
        String requestPath = requestLine.split(" ")[1].trim();
        Map<String, String> requestHeaders = parseRequestHeaders(in);
        String requestBody = parseRequestBody(in, requestHeaders);

        return new Request(requestLine, requestPath, requestHeaders, requestBody);
    }

    private static String parseRequestLine(BufferedReader in) {
        String requestLine = null;
        try {
            requestLine = in.readLine();
        } catch (IOException err) {
            System.err.println(err);
        }
        return requestLine;
    }

    private static Map parseRequestHeaders(BufferedReader in) {
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

    private static String parseRequestBody(BufferedReader in, Map requestHeaders) {
        Integer contentLength = parseRequestBodyContentLength(requestHeaders);
        char[] requestBody = new char[contentLength];

        try {
            in.read(requestBody, 0, contentLength);
        } catch (IOException err) {
            System.err.println(err);
        }
        return new String(requestBody);
    }

    private static Integer parseRequestBodyContentLength(Map<String, String> requestHeaders) {
        String contentLengthStr = requestHeaders.get("Content-Length");
        return contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);
    }
}
