package server;

import server.request.Request;
import server.request.RequestParser;
import server.response.ResponseFormatter;
import server.response.Response;
import server.response.ResponseSelector;

public class ConnectionHandler implements Runnable {
    private Client client;
    private RequestParser parser;
    private ResponseSelector selector;
    private ResponseFormatter formatter;

    public ConnectionHandler(Client client, RequestParser parser, ResponseSelector selector, ResponseFormatter formatter) {
        this.client = client;
        this.parser = parser;
        this.selector = selector;
        this.formatter = formatter;
    }

    public void run() {
        Request request = parser.parse();
        Response response = selector.selectResponse(request);
        sendResponse(response);
        client.closeSocket();
    }

    private void sendResponse(Response response) {
        client.write(formatter.statusLine(response));
        client.write(formatter.headers(response));
        client.write(response.body());
    }
}
