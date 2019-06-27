package app;

import server.Server;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        String directory = args[1];
        App app = new App(new Server(port), Routes.ROUTES, directory);
        app.start();
    }
}
