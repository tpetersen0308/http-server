package server;

import resources.Logger;
import server.request.Parser;
import server.request.Handler;
import server.response.Formatter;
import server.response.Selector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private Integer port;
    private Logger logger = new Logger();

    public Server(Integer port) {
        this.port = port;
    }

    public void start(Map<String, Map<String, Handler>> routes) {
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = socket.accept();
                Client client = new Client(clientSocket);
                Parser parser = new Parser(client);
                Selector selector = new Selector(routes);
                Formatter formatter = new Formatter();
                ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);
                (new Thread(connectionHandler)).start();
            }
        } catch (IOException err) {
            logger.log(err);
        }
    }
}
