package server;

import org.junit.Test;
import server.response.Response;
import server.response.HTTPResponseFormatter;
import server.response.types.OKResponse;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponseFormatterTest {
    @Test
    public void shouldReturnResponseParsedIntoString() {
        Map<String, String> headers = new HashMap() {{ put("Allow", "GET, HEAD, OPTIONS"); }};
        Response response = new OKResponse(headers, "lorem ipsum dolor sit amet, adipiscing elit...");
        HTTPResponseFormatter formatter = new HTTPResponseFormatter(response);

        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD, OPTIONS\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...", formatter.stringifyResponse());
    }
}
