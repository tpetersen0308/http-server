package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    public HashMap parse(BufferedReader in) {
        HashMap parsedRequest = new HashMap();
        try {
            String requestLine = parseRequestLine(in);
            parsedRequest.put("requestLine", requestLine);

            HashMap<String, String> requestHeaders = parseRequestHeaders(in);
            parsedRequest.put("requestHeaders", requestHeaders);

            String contentLengthStr = requestHeaders.get("Content-Length");
            Integer contentLengthInt = contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);

            StringBuilder requestBody = new StringBuilder();
            int nextBodyChar;

            for(int i = 0; i < contentLengthInt; i++) {
                nextBodyChar = in.read();
                requestBody.append((char)nextBodyChar);
            }

            parsedRequest.put("requestBody", requestBody.toString());

        } catch (IOException e) {
            System.err.println(e);
        }
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
            if(header != null) {
                while(!header.isEmpty()) {
                    String[] headerComponents = header.split(": ");
                    requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
                    header = in.readLine();
                }
            }
        } catch(IOException err) {
            System.err.println(err);
        }
        return requestHeaders;
    }
}