package server.response;

import java.util.Map;

public class Response {
    private String statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;

    public Response(String statusCode, String reasonPhrase, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
    }

    public String statusCode() {
        return statusCode;
    }

    public String reasonPhrase() {
        return reasonPhrase;
    }

    public Map<String, String> headers() {
        return headers;
    }
}
