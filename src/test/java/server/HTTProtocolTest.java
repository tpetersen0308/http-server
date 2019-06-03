package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.stubs.SocketStub;

import java.io.IOException;

public class HTTProtocolTest {

    @Test
    public void shouldReturnNotFound() throws IOException {
        SocketStub socket = new SocketStub("GET /not_found_resource HTTP/1.1");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkForSimpleHeadRequest() throws IOException {
        SocketStub socket = new SocketStub("HEAD /simple_get HTTP/1.1");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithNoBodyForSimpleHeadRequest() throws IOException {
        SocketStub socket = new SocketStub("HEAD /get_with_body HTTP/1.1");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithAllowedHeadersForSimpleOptionsRequest() throws IOException {
        SocketStub socket = new SocketStub("OPTIONS /method_options HTTP/1.1");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD, OPTIONS\r\n\r\n", socket.getOutputStream().toString());
    }

    @Test
    public void shouldReturnOkWithRequestBodyForSimplePostRequest() throws IOException {
        SocketStub socket = new SocketStub("POST /echo_body HTTP/1.1\r\nContent-Length: 46\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD, OPTIONS, POST\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...", socket.getOutputStream().toString());
    }
}
