package app;

import java.util.Map;

public class App {
    private Map<String, Map<String, MethodApplier>> routes;

    public App(Map<String, Map<String, MethodApplier>> routes) {
        this.routes = routes;
    }

    public Map<String, Map<String, MethodApplier>> routes() {
        return routes;
    }
}
