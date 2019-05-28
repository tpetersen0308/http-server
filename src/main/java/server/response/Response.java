package server.response;

import server.Request;

import java.util.Arrays;

import static server.Routes.ROUTES;

public class Response {
    private Request request;
    private String status;

    public Response(Request request) {
        this.request = request;
        this.status = this.setStatus();
    }

    public String getStatus() {
        return status;
    }

    private String setStatus() {
        if (Arrays.asList(ROUTES).contains(request.getPath())) {
            return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + StatusCodes.OK + ResponseComponents.SP + ReasonPhrases.OK + ResponseComponents.CRLF + ResponseComponents.CRLF;
        } else {
            return ResponseComponents.HTTP_VERSION + ResponseComponents.SP + StatusCodes.NOT_FOUND + ResponseComponents.SP + ReasonPhrases.NOT_FOUND + ResponseComponents.CRLF + ResponseComponents.CRLF;
        }
    }
}
