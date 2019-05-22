package server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import server.stubs.SocketStub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RequestParserTest {
    String request;
    String requestLine;
    String requestBody;
    SocketStub socket;
    BufferedReader in;
    RequestParser parser;
    HashMap expectedHeaders;

    @Before
    public void stubRequest() {
        requestLine = "GET /redirect HTTP/1.1";
        String requestHeaders = "User-Agent: Ruby\n" + "Connection: close\n" + "Host: 127.0.0.1:5000\n" + "Content-Length: 46";
        requestBody = "lorem ipsum dolor sit amet, adipiscing elit...";
        request = requestLine + "\r\n" + requestHeaders + "\n\n" + requestBody;
    }

    @Before
    public void stubHeaders() {
        expectedHeaders = new HashMap<String, String>();
        expectedHeaders.put("User-Agent", "Ruby");
        expectedHeaders.put("Connection", "close");
        expectedHeaders.put("Host", "127.0.0.1:5000");
        expectedHeaders.put("Content-Length", "46");
    }

    @Before
    public void setupInputStreamReader() {
        socket = new SocketStub(request);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Before
    public void setupParser() {
        parser = new RequestParser();
    }

    @Test
    public void shouldReturnEntireParsedRequest() {
        HashMap expectedResult = new HashMap<>();
        expectedResult.put("requestLine", requestLine);
        expectedResult.put("requestHeaders", expectedHeaders);
        expectedResult.put("requestBody", requestBody);

        assertEquals(expectedResult, parser.parse(in));
    }

    @Test
    public void shouldParseRequestLineFromInputStream() {
        assertEquals("GET /redirect HTTP/1.1", parser.parseRequestLine(in));
    }

    @Test
    public void shouldParseHeadersFromInputStream() throws IOException {
        in.readLine();

        assertEquals(expectedHeaders, parser.parseRequestHeaders(in));
    }
}
