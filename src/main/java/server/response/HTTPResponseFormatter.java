package server.response;

import server.response.stringcomponents.HTTP;
import server.response.stringcomponents.WhiteSpace;

import java.util.Map;

public class HTTPResponseFormatter {
    private Response response;

    public HTTPResponseFormatter(Response response) {
        this.response = response;
    }

    public String stringifyResponse() {
        return stringifyStatusLine()+ WhiteSpace.CRLF + stringifyHeaders() + WhiteSpace.CRLF + response.body();
    }

    private String stringifyStatusLine() {
        return HTTP.VERSION + WhiteSpace.SP + response.status();
    }

    private String stringifyHeaders() {
        StringBuilder headers = new StringBuilder();
        for(Map.Entry<String, String> header : response.headers().entrySet()) {
            headers.append(header.getKey());
            headers.append(": ");
            headers.append(header.getValue());
            headers.append(WhiteSpace.CRLF);
        }
        return headers.toString();
    }
}
