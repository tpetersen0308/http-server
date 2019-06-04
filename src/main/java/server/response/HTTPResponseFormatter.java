package server.response;

import java.util.Map;

public class HTTPResponseFormatter {

    public static String stringify(Response response) {
        return stringifyStatusLine(response)+ ResponseComponents.CRLF + stringifyHeaders(response) + ResponseComponents.CRLF + response.body();
    }

    private static String stringifyStatusLine(Response response) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + response.statusCode() + ResponseComponents.SP + response.reasonPhrase();
    }

    private static String stringifyHeaders(Response response) {
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
