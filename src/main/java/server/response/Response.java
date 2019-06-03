package server.response;

import java.util.Map;

public class Response {
    private String statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private String body;

    public Response(String statusCode, String reasonPhrase, Map<String, String> headers, String body) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
        this.body = body;
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

    public String body() {
        return body;
    }
}
