package server.response.types;

import server.response.ReasonPhrases;
import server.response.Response;
import server.response.StatusCodes;

import java.util.Map;

public class NotFoundResponse extends Response {
    public NotFoundResponse(Map<String, String> headers, String body) {
        super(headers, body);
        this.statusCode = StatusCodes.NOT_FOUND;
        this.reasonPhrase = ReasonPhrases.NOT_FOUND;
    }
}
