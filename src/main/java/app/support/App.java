package app.support;

import java.util.Map;

public class App {
    private Map<String, Map<String, ActionDispatcher>> routes;

    public App(Map<String, Map<String, ActionDispatcher>> routes) {
        this.routes = routes;
    }

    public Map<String, Map<String, ActionDispatcher>> routes() {
        return routes;
    }
}
