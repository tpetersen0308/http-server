package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import server.stubs.SocketStub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RequestParserTest {
    @Test
    public void shouldReturnEntireParsedRequest() {
        String requestLine = "GET /redirect HTTP/1.1";
        String requestHeaders = "User-Agent: Ruby\n" + "Connection: close\n" + "Host: 127.0.0.1:5000\n" + "Content-Length: 9";
        String requestBody = "some body"; //"lorem ipsum dolor sit amet, adipiscing elit...";
        String request = requestLine + "\r\n" + requestHeaders + "\n\n" + requestBody;

        HashMap headersMap = new HashMap<String, String>();
        headersMap.put("User-Agent", "Ruby");
        headersMap.put("Connection", "close");
        headersMap.put("Host", "127.0.0.1:5000");
        headersMap.put("Content-Length", "9");

        HashMap expectedResult = new HashMap<>();
        expectedResult.put("requestLine", requestLine);
        expectedResult.put("requestHeaders", headersMap);
        expectedResult.put("requestBody", requestBody);


        SocketStub socket = new SocketStub(request);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        RequestParser parser = new RequestParser(in);

        assertEquals(expectedResult, parser.parse());
    }
}
