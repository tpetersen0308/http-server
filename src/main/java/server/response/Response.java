package server.response;

import java.util.Map;

public class Response {
    protected String statusCode = "";
    protected String reasonPhrase = "";
    protected Map<String, String> headers;
    protected String body;

    public Response(Map<String, String> headers, String body) {
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
