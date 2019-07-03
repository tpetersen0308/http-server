package server.response;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class FormatterTest {
    Response response;

    @Before
    public void setUpResponse() {
        response = new Response.Builder()
                .withStatus("200 OK")
                .withHeader("Allow", "GET, HEAD, OPTIONS")
                .build();
    }

    @Test
    public void shouldFormatStatusLine() {
        Formatter formatter = new Formatter();

        assertArrayEquals("HTTP/1.1 200 OK\r\n".getBytes(), formatter.statusLine(response));
    }

    @Test
    public void shouldStringifyHeaders() {
        Formatter formatter = new Formatter();

        assertArrayEquals("Allow: GET, HEAD, OPTIONS\r\n\r\n".getBytes(), formatter.headers(response));
    }
}
