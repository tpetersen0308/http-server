package server.response;

public class ResponseParser {

    public static String parse(Response response) {
        return parseStatusLine(response)+ ResponseComponents.CRLF + parseHeaders(response) + ResponseComponents.CRLF + response.body();
    }

    private static String parseStatusLine(Response response) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.statusCode() + ResponseComponents.SP + response.reasonPhrase();
    }

    private static String parseHeaders(Response response) {

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
