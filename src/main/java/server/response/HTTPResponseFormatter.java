package server.response;

public class HTTPResponseFormatter {

    public static String stringifyResponse(Response response) {
        return stringifyStatusLine(response)+ ResponseComponents.CRLF + stringifyHeaders(response) + ResponseComponents.CRLF + response.body();
    }

    private static String stringifyStatusLine(Response response) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.statusCode() + ResponseComponents.SP + response.reasonPhrase();
    }

    private static String stringifyHeaders(Response response) {

        if(response.headers().size() > 0) {
            return response.headers().toString()
                    .replace("{", "")
                    .replace("}", "")
                    .replace("=", ": ") + ResponseComponents.CRLF;
        } else {
            return "";
        }
    }
}
