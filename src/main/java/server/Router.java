package server;

import server.request.Handler;
import server.response.stringcomponents.*;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Map<String, Handler>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public Map<String, Map<String, Handler>> routes() {
        return routes;
    }

    public void get(String path, Handler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.GET, action);
        head(path, action);
    }

    public void head(String path, Handler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.HEAD, action);
    }

    public void options(String path, Handler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.OPTIONS, action);
    }

    public void post(String path, Handler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.POST, action);
    }

    public void put(String path, Handler action) {
        addRoute(path);
        routes.get(path).put(HTTPMethods.PUT, action);
    }

    private void addRoute(String path) {
        routes.putIfAbsent(path, new HashMap<>());
    }
}
