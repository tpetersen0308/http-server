package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private Integer port;

    public Server(Integer port) throws IOException {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        Client client = new Client(socket.accept());
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
    }
}