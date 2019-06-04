package app;

import server.request.Request;

public class Methods {
    public static MethodApplier SIMPLE_GET = (Request request) -> "";
    public static MethodApplier GET_WITH_BODY = (Request request) -> "some body";
    public static MethodApplier SIMPLE_OPTIONS = (Request request) -> "";
    public static MethodApplier ECHO_BODY = (Request request) -> request.body();
}
