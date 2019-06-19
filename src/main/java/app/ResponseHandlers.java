package app;

import app.support.ResponseHandler;
import app.support.ResponseHelpers;
import server.request.Request;

public class ResponseHandlers {
    public static final ResponseHandler SIMPLE_GET = (Request request) -> ResponseHelpers.render(request, "");
    public static final ResponseHandler GET_WITH_BODY = (Request request) -> ResponseHelpers.render(request, "some body");
    public static final ResponseHandler SIMPLE_OPTIONS = (Request request) -> ResponseHelpers.render(request, "");
    public static final ResponseHandler ECHO_BODY = (Request request) -> ResponseHelpers.render(request, request.body());
    public static final ResponseHandler REDIRECT = (Request request) -> ResponseHelpers.redirectTo(request,"/simple_get");
}
