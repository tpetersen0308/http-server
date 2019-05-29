package server.response;

import server.Request;

import java.util.Arrays;

import static server.Routes.ROUTES;

public class Response {
    private Request request;
    private String statusLine;

    public Response(Request request) {
        this.request = request;
        this.statusLine = this.setStatusLine();
    }

    public String getStatusLine() {
        return statusLine;
    }

    private String setStatusLine() {
        if (Arrays.asList(ROUTES).contains(request.path())) {
            return formatStatusLine(StatusCodes.OK, ReasonPhrases.OK);
        } else {
            return formatStatusLine(StatusCodes.NOT_FOUND, ReasonPhrases.NOT_FOUND);
        }
    }

    private String formatStatusLine(String code, String reason) {
        return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + code + ResponseComponents.SP + reason + ResponseComponents.CRLF + ResponseComponents.CRLF;
    }
}
