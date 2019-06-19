package app.support;

import server.response.stringcomponents.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    private Map<String, Map<String, ResponseHandler>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public Map<String, Map<String, ResponseHandler>> routes() {
        return routes;
    }

    public void get(String path, ResponseHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.GET, action);
        head(path, action);
    }

    public void head(String path, ResponseHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.HEAD, action);
    }

    public void options(String path, ResponseHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.OPTIONS, action);
    }

    public void post(String path, ResponseHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.POST, action);
    }

    public void put(String path, ResponseHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.PUT, action);
    }

    private void addRoute(String path) {
        routes.putIfAbsent(path, new LinkedHashMap<>());
    }
}
