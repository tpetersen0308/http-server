package server.response;

public class Response {
    private String statusLine;

    public Response(String statusCode, String reasonPhrase) {
        this.statusLine = ResponseComponents.HTTP_VERSION + ResponseComponents.SP + statusCode + ResponseComponents.SP + reasonPhrase + ResponseComponents.CRLF + ResponseComponents.CRLF;
    }

    public String statusLine() {
        return statusLine;
    }
}
