package app;

import server.RequestHandler;
import server.Server;
import server.directory.DefaultDirectory;

import java.util.Map;

public class App {
    private Server server;
    private Map<String, Map<String, RequestHandler>> routes;
    private String directory;

    public App(Server server, Map<String, Map<String, RequestHandler>> routes, String directory) {
        this.server = server;
        this.routes = routes;
        this.directory = directory;
    }

    public void start() {
        DefaultDirectory.setPath(directory);
        server.start(routes);
    }
}
