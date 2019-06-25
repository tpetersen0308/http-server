package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.request.Parser;
import server.response.Formatter;
import server.response.Selector;
import stubs.server.ClientStub;
import stubs.server.RoutesStub;
import stubs.server.SocketStub;

public class ConnectionHandlerTest {
    @Test
    public void shouldRespondToProperlyFormattedRequests() {
        SocketStub socket = new SocketStub("GET /test-route HTTP/1.1\r\n\r\n");
        Client client = new Client(socket);
        Parser parser = new Parser(client);
        Selector selector = new Selector(RoutesStub.ROUTES);
        Formatter formatter = new Formatter();
        ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 9\r\nAllow: GET, HEAD\r\n\r\ntest body";

        connectionHandler.run();

        assertEquals(expected, socket.getOutputStream().toString());
    }

    @Test
    public void shouldRespondWhenInternalErrorIsThrown() {
        SocketStub socket = new SocketStub("GET /test-route HTTP/1.1\r\n\r\n");
        Client client = new ClientStub(socket);
        Parser parser = new Parser(client);
        Selector selector = new Selector(RoutesStub.ROUTES);
        Formatter formatter = new Formatter();
        ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);

        String expected = "HTTP/1.1 500 Internal Server Error\r\n\r\n";

        connectionHandler.run();

        assertEquals(expected, socket.getOutputStream().toString());
    }

    @Test
    public void shouldRespondToBadRequest() {
        SocketStub socket = new SocketStub("");
        Client client = new Client(socket);
        Parser parser = new Parser(client);
        Selector selector = new Selector(RoutesStub.ROUTES);
        Formatter formatter = new Formatter();
        ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);

        String expected = "HTTP/1.1 400 Bad Request\r\n\r\n";

        connectionHandler.run();

        assertEquals(expected, socket.getOutputStream().toString());
    }
}
