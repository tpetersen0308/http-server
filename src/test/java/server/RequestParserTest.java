package server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.request.Request;
import server.request.RequestParser;
import stubs.server.SocketStub;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestParserTest {
    String request;
    String requestLine;
    String requestBody;
    String requestHeaders;
    SocketStub socket;
    BufferedReader in;

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
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Test
    public void shouldParseRequestMethod() {
        RequestParser requestParser = new RequestParser(in);
        Request parsedRequest = requestParser.parse();

        assertEquals("GET", parsedRequest.method());
    }

    @Test
    public void shouldParseRequestPath() {
        RequestParser requestParser = new RequestParser(in);
        Request parsedRequest = requestParser.parse();

        assertEquals("/redirect", parsedRequest.path());
    }

    @Test
    public void shouldParseRequestHeaders() {
        RequestParser requestParser = new RequestParser(in);
        Request parsedRequest = requestParser.parse();

        assertEquals("Ruby", parsedRequest.headers().get("User-Agent"));
        assertEquals("close", parsedRequest.headers().get("Connection"));
        assertEquals("127.0.0.1:5000", parsedRequest.headers().get("Host"));
        assertEquals("46", parsedRequest.headers().get("Content-Length"));
    }

    @Test
    public void shouldParseRequestBody() {
        RequestParser requestParser = new RequestParser(in);
        Request parsedRequest = requestParser.parse();

        assertEquals(requestBody, parsedRequest.body());
    }
}
