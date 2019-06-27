package server.response;

import server.response.stringcomponents.HTTP;
import server.response.stringcomponents.WhiteSpace;

import java.util.Map;

public class ResponseFormatter {
    public byte[] statusLine(Response response) {
        return (HTTP.VERSION + WhiteSpace.SP + response.status() + WhiteSpace.CRLF).getBytes();
    }

    public byte[] headers(Response response) {
        StringBuilder headers = new StringBuilder();
        for(Map.Entry<String, String> header : response.headers().entrySet()) {
            headers.append(header.getKey());
            headers.append(": ");
            headers.append(header.getValue());
            headers.append(WhiteSpace.CRLF);
        }
        return headers.append(WhiteSpace.CRLF).toString().getBytes();
    }
}
