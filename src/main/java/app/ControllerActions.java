package app;

import app.support.ActionDispatcher;
import app.support.ActionHelpers;
import server.request.Request;

public class ControllerActions {
    public static final ActionDispatcher SIMPLE_GET = (Request request) -> ActionHelpers.render(request, "");
    public static final ActionDispatcher GET_WITH_BODY = (Request request) -> ActionHelpers.render(request, "some body");
    public static final ActionDispatcher SIMPLE_OPTIONS = (Request request) -> ActionHelpers.render(request, "");
    public static final ActionDispatcher ECHO_BODY = (Request request) -> ActionHelpers.render(request, request.body());
    public static final ActionDispatcher REDIRECT = (Request request) -> ActionHelpers.redirectTo(request,"/simple_get");
}
