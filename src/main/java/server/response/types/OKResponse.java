package server.response.types;

import server.response.ReasonPhrases;
import server.response.Response;
import server.response.StatusCodes;

import java.util.Map;

public class OKResponse extends Response {
    public OKResponse(Map<String, String> headers, String body) {
        super(headers, body);
        this.statusCode = StatusCodes.OK;
        this.reasonPhrase = ReasonPhrases.OK;
    }
}
