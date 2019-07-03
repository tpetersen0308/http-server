package server;

import resources.Logger;
import server.request.Request;
import server.request.Parser;
import server.response.Formatter;
import server.response.Response;
import server.response.Selector;

import java.io.IOException;

public class ConnectionHandler implements Runnable {
    private Client client;
    private Parser parser;
    private Selector selector;
    private Formatter formatter;
    private Logger logger = new Logger();

    public ConnectionHandler(Client client, Parser parser, Selector selector, Formatter formatter) {
        this.client = client;
        this.parser = parser;
        this.selector = selector;
        this.formatter = formatter;
    }

    public void run() {
        Request request;
        Response response;
        try {
            request = parser.parse(client);
            response = selector.selectResponse(request);
        } catch (IOException err) {
            logger.log(err);
            response = selector.selectResponse(err);
        }
        sendResponse(response);
        client.closeSocket();
    }

    private void sendResponse(Response response) {
        client.write(formatter.statusLine(response));
        client.write(formatter.headers(response));
        client.write(response.body());
    }
}
