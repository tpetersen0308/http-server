package server;

import app.support.App;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Integer port;
    private App app;

    public Server(Integer port, App app) {
        this.port = port;
        this.app = app;
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = socket.accept();
            Client client = new Client(clientSocket);
            HTTProtocol protocol = new HTTProtocol(client, app);
            (new Thread(protocol)).start();
        }
    }
}
