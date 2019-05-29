package server.response;

import server.request.Request;

import java.util.Arrays;

import static server.Routes.ROUTES;

public class ResponseBuilder {
    public static Response buildResponse(Request request) {
        if (Arrays.asList(ROUTES).contains(request.path())) {
            return new Response(StatusCodes.OK, ReasonPhrases.OK);
        } else {
            return new Response(StatusCodes.NOT_FOUND, ReasonPhrases.NOT_FOUND);
        }
    }
}
