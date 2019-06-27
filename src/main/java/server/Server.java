package server;

import server.request.RequestParser;
import server.response.ResponseFormatter;
import server.response.ResponseSelector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private Integer port;

    public Server(Integer port) {
        this.port = port;
    }

    public void start(Map<String, Map<String, RequestHandler>> routes) {
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = socket.accept();
                Client client = new Client(clientSocket);
                RequestParser parser = new RequestParser(client);
                ResponseSelector selector = new ResponseSelector(routes);
                ResponseFormatter formatter = new ResponseFormatter();
                ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);
                (new Thread(connectionHandler)).start();
            }
        } catch (IOException err) {
            System.out.println(err);
        }
    }
}
