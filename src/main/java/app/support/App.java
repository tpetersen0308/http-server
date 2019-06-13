package app.support;

import java.util.Map;

public class App {
    private Map<String, Map<String, ResponseHandler>> routes;

    public App(Map<String, Map<String, ResponseHandler>> routes) {
        this.routes = routes;
    }

    public Map<String, Map<String, ResponseHandler>> routes() {
        return routes;
    }
}
