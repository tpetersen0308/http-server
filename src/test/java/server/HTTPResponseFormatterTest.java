package server;

import org.junit.Test;
import server.response.HTTPResponseFormatter;
import server.response.Response;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class HTTPResponseFormatterTest {
    @Test
    public void shouldReturnResponseParsedIntoString() {
        Map<String, String> headers = new HashMap() {{
            put("Allow", "GET, HEAD, OPTIONS");
        }};
        Response response = new Response.Builder()
            .withStatus("200 OK")
            .withCustomHeaders(headers)
            .withBody("lorem ipsum dolor sit amet, adipiscing elit...")
            .build();

        HTTPResponseFormatter formatter = new HTTPResponseFormatter(response);

        assertEquals("HTTP/1.1 200 OK\r\nAllow: GET, HEAD, OPTIONS\r\n\r\nlorem ipsum dolor sit amet, adipiscing elit...", formatter.stringifyResponse());
    }
}
