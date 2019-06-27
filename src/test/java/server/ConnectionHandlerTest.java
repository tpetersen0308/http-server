package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.request.RequestParser;
import server.response.ResponseFormatter;
import server.response.ResponseSelector;
import stubs.server.RoutesStub;
import stubs.server.SocketStub;

public class ConnectionHandlerTest {
    @Test
    public void shouldRespondToProperlyFormattedRequests() {
        SocketStub socket = new SocketStub("GET /test-route HTTP/1.1\r\n\r\n");
        Client client = new Client(socket);
        RequestParser parser = new RequestParser(client);
        ResponseSelector selector = new ResponseSelector(RoutesStub.ROUTES);
        ResponseFormatter formatter = new ResponseFormatter();
        ConnectionHandler connectionHandler = new ConnectionHandler(client, parser, selector, formatter);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 9\r\nAllow: GET, HEAD\r\n\r\ntest body";

        connectionHandler.run();

        assertEquals(expected, socket.getOutputStream().toString());
    }
}
