package server.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.Client;
import stubs.server.SocketStub;

import java.io.IOException;

public class ParserTest {
    String request;
    String requestLine;
    String requestBody;
    String requestHeaders;
    SocketStub socket;
    Client client;

    @Before
    public void stubRequest() {
        requestLine = "GET /redirect HTTP/1.1";
        requestHeaders = "User-Agent: Ruby\r\n" + "Connection: close\r\n" + "Host: 127.0.0.1:5000\r\n" + "Content-Length: 46\r\n";
        requestBody = "lorem ipsum dolor sit amet, adipiscing elit...";
        request = requestLine + "\r\n" + requestHeaders + "\r\n" + requestBody;
    }

    @Before
    public void setupInputStreamReader() {
        socket = new SocketStub(request);
        client = new Client(socket);
    }

    @Test
    public void shouldParseRequestMethod() throws IOException {
        Parser parser = new Parser();
        Request parsedRequest = parser.parse(client);

        assertEquals("GET", parsedRequest.method());
    }

    @Test
    public void shouldParseRequestPath() throws IOException{
        Parser parser = new Parser();
        Request parsedRequest = parser.parse(client);

        assertEquals("/redirect", parsedRequest.path());
    }

    @Test
    public void shouldParseRequestHeaders() throws IOException{
        Parser parser = new Parser();
        Request parsedRequest = parser.parse(client);

        assertEquals("Ruby", parsedRequest.headers().get("User-Agent"));
        assertEquals("close", parsedRequest.headers().get("Connection"));
        assertEquals("127.0.0.1:5000", parsedRequest.headers().get("Host"));
        assertEquals("46", parsedRequest.headers().get("Content-Length"));
    }

    @Test
    public void shouldParseRequestBody() throws IOException {
        Parser parser = new Parser();
        Request parsedRequest = parser.parse(client);

        assertEquals(requestBody, parsedRequest.body());
    }
}
