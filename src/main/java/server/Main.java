package server;

import app.App;
import app.Routes;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        App app = new App(Routes.ROUTES);
        Server server = new Server(port, app);
        server.start();
    }
}
