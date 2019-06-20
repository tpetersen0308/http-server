package server;

import server.response.stringcomponents.*;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Map<String, RequestHandler>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public Map<String, Map<String, RequestHandler>> routes() {
        return routes;
    }

    public void get(String path, RequestHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.GET, action);
        head(path, action);
    }

    public void head(String path, RequestHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.HEAD, action);
    }

    public void options(String path, RequestHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.OPTIONS, action);
    }

    public void post(String path, RequestHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.POST, action);
    }

    public void put(String path, RequestHandler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.PUT, action);
    }

    private void addRoute(String path) {
        routes.putIfAbsent(path, new HashMap<>());
    }
}
