package server.response;

public class ResponseHTTPFormatter {

    public static String formatHTTPResponse(Response response) {
        return formatStatusLineForHTTPResponse(response)+ ResponseComponents.CRLF + formatHeadersForHTTPResponse(response) + ResponseComponents.CRLF + response.body();
    }

    private static String formatStatusLineForHTTPResponse(Response response) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.statusCode() + ResponseComponents.SP + response.reasonPhrase();
    }

    private static String formatHeadersForHTTPResponse(Response response) {

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
