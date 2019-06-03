package server.router;

import server.request.Request;

public class Methods {
    public static MethodApplier SIMPLE_GET = (Request request) -> "";
    public static MethodApplier SIMPLE_OPTIONS = (Request request) -> "";
    public static MethodApplier ECHO_BODY = (Request request) -> request.body();
}
