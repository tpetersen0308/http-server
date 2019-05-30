package server.response;

public class ResponseParser {

    public static String parse(Response response) {
        return parseStatusLine(response) + parseHeaders(response);
    }

    private static String parseStatusLine(Response response) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.statusCode() + ResponseComponents.SP + response.reasonPhrase() + ResponseComponents.CRLF;
    }

    private static String parseHeaders(Response response) {
        return response.headers().toString().replace("{", "").replace("}", "").replace("=", ": ") + ResponseComponents.CRLF;
    }
}
