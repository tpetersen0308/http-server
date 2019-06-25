package server;

import app.support.App;
import app.Routes;
import app.support.DefaultDirectory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        DefaultDirectory.setPath(args[1]);
        App app = new App(Routes.ROUTES);
        Server server = new Server(port, app);
        server.start();
    }
}
