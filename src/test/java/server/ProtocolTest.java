package server;

import app.support.App;
import app.Routes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import stubs.server.SocketStub;

import java.io.IOException;

public class ProtocolTest {
    App app;

    @Before
    public void setupApp() {
        app = new App(Routes.ROUTES);
    }

    @Test
    public void shouldReturnNotFound() throws IOException {
        SocketStub socket = new SocketStub("GET /not_found_resource HTTP/1.1");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkForSimpleHeadRequest() throws IOException {
        SocketStub socket = new SocketStub("HEAD /simple_get HTTP/1.1");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithNoBodyForSimpleHeadRequest() throws IOException {
        SocketStub socket = new SocketStub("HEAD /get_with_body HTTP/1.1");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 9\r\nAllow: HEAD, OPTIONS\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithAllowedHeadersForSimpleOptionsRequest() throws IOException {
        SocketStub socket = new SocketStub("OPTIONS /method_options HTTP/1.1");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithAllowedHeadersForComplexOptionsRequest() throws IOException {
        SocketStub socket = new SocketStub("OPTIONS /method_options2 HTTP/1.1");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 0\r\nAllow: GET, HEAD, OPTIONS, POST, PUT\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithRequestBodyForSimplePostRequest() throws IOException {
        SocketStub socket = new SocketStub("POST /echo_body HTTP/1.1\r\nContent-Length: 46\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 46\r\nAllow: GET, HEAD, OPTIONS, POST\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnMethodNotAllowed() throws IOException {
        SocketStub socket = new SocketStub("GET /get_with_body HTTP/1.1\r\nContent-Length: 0\r\n\r\n");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 405 Method Not Allowed\r\nAllow: HEAD, OPTIONS\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnMovedPermanently() throws IOException {
        SocketStub socket = new SocketStub("GET /redirect HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n");
        Client client = new Client(socket);
        Protocol protocol = new Protocol(client, app);
        protocol.run();
        assertEquals("HTTP/1.1 301 Moved Permanently\r\nLocation: http://127.0.0.1:5000/simple_get\r\n\r\n", socket.getOutputStream().toString());
    }
}
