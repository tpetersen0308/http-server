package app;

import server.response.stringcomponents.HTTPMethods;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    Map<String, Map<String, MethodApplier>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public void get(String path, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.GET, method);
        head(path, method);
    }

    public void head(String path, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.HEAD, method);
    }

    public void options(String path, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.OPTIONS, method);
    }

    public void post(String path, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.POST, method);
    }
    public void put(String path, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.PUT, method);
    }

    private void addRoute(String path) {
        routes.putIfAbsent(path, new LinkedHashMap<>());
    }
}
