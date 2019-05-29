package server.response;

import server.request.Request;

import java.util.Map;

import static server.Routes.ROUTES;

public class ResponseBuilder {
    public static Response buildResponse(Request request) {
        Map<String, String> route = ROUTES.get(request.path());
        if (route != null) {
            return new Response(StatusCodes.OK, ReasonPhrases.OK);
        } else {
            return new Response(StatusCodes.NOT_FOUND, ReasonPhrases.NOT_FOUND);
        }
    }
}
