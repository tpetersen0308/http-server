package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    public HashMap parse(BufferedReader in) {
        HashMap parsedRequest = new HashMap();
        try {
            String requestLine = in.readLine();
            parsedRequest.put("requestLine", requestLine);

            HashMap<String, String> requestHeaders = new HashMap<>();
            String header = in.readLine();
            if(header != null) {
                while(!header.isEmpty()) {
                    String[] headerComponents = header.split(": ");
                    requestHeaders.put(headerComponents[0].trim(), headerComponents[1].trim());
                    header = in.readLine();
                }

                parsedRequest.put("requestHeaders", requestHeaders);
            } else {
                parsedRequest.put("requestHeaders", "");
            }

            StringBuilder requestBody = new StringBuilder();
            int nextBodyChar;

            String contentLengthStr = requestHeaders.get("Content-Length");
            Integer contentLengthInt = contentLengthStr == null ? 0 : Integer.parseInt(contentLengthStr);

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
}