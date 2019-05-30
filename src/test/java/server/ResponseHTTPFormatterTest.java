package server;

import org.junit.Test;
import server.response.Response;
import server.response.ResponseHTTPFormatter;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ResponseHTTPFormatterTest {
    @Test
    public void shouldReturnResponseParsedIntoString() {
        Map<String, String> headers = new HashMap() {{ put("Allow", "GET, HEAD, OPTIONS"); }};
        Response response = new Response("200", "OK", headers, "lorem ipsum dolor sit amet, adipiscing elit...");

        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD, OPTIONS\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...", ResponseHTTPFormatter.formatHTTPResponse(response));
    }
}
