package app;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Router {
    Map<String, Map<String, MethodApplier>> routes;

    public Router() {
        this.routes = new HashMap<>();
    }

    public void addMethodToRoute(String path, String methodName, MethodApplier method) {
        addRoute(path);
        routes.get(path).put(methodName, method);
        if(methodName.equals("GET")){
            addMethodToRoute(path, "HEAD", method);
        }
    }

    private void addRoute(String path) {
        routes.putIfAbsent(path, new LinkedHashMap<>());
    }
}
