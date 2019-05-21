package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Integer port;

    public Server(Integer port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        while(true) {
            Socket clientSocket = socket.accept();
            Client client = new Client(clientSocket);
            HTTProtocol protocol = new HTTProtocol(client);
            (new Thread(protocol)).start();
        }
    }
}