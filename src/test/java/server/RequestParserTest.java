package server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.request.Request;
import server.request.RequestParser;
import server.stubs.SocketStub;

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
        requestHeaders = "User-Agent: Ruby\n" + "Connection: close\n" + "Host: 127.0.0.1:5000\n" + "Content-Length: 46";
        requestBody = "lorem ipsum dolor sit amet, adipiscing elit...";
        request = requestLine + "\r\n" + requestHeaders + "\n\n" + requestBody;
    }

    @Before
    public void setupInputStreamReader() {
        socket = new SocketStub(request);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Test
    public void shouldParseRequestPath() {
        Request parsedRequest = RequestParser.parse(in);

        assertEquals("/redirect", parsedRequest.path());
    }

    @Test
    public void shouldParseRequestHeaders() {
        Request parsedRequest = RequestParser.parse(in);

        assertEquals("Ruby", parsedRequest.headers().get("User-Agent"));
        assertEquals("close", parsedRequest.headers().get("Connection"));
        assertEquals("127.0.0.1:5000", parsedRequest.headers().get("Host"));
        assertEquals("46", parsedRequest.headers().get("Content-Length"));
    }

    @Test
    public void shouldParseRequestBody() {
        Request parsedRequest = RequestParser.parse(in);

        assertEquals(requestBody, parsedRequest.body());
    }
}
