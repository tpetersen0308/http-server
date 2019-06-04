package server.response;

import java.util.Map;

public class HTTPResponseFormatter {
    private Response response;

    public HTTPResponseFormatter(Response response) {
        this.response = response;
    }

    public String stringifyResponse() {
        return stringifyStatusLine()+ ResponseComponents.CRLF + stringifyHeaders() + ResponseComponents.CRLF + response.body();
    }

    private String stringifyStatusLine() {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.status();
    }

    private String stringifyHeaders() {
        StringBuilder headers = new StringBuilder();
        for(Map.Entry<String, String> header : response.headers().entrySet()) {
            headers.append(header.getKey());
            headers.append(": ");
            headers.append(header.getValue());
            headers.append(ResponseComponents.CRLF);
        }
        return headers.toString();
    }
}
