package server.response;

import org.junit.Before;
import org.junit.Test;
import server.response.ResponseFormatter;
import server.response.Response;

import static org.junit.Assert.assertArrayEquals;

public class ResponseFormatterTest {
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
        ResponseFormatter formatter = new ResponseFormatter();

        assertArrayEquals("HTTP/1.1 200 OK\r\n".getBytes(), formatter.statusLine(response));
    }

    @Test
    public void shouldStringifyHeaders() {
        ResponseFormatter formatter = new ResponseFormatter();

        assertArrayEquals("Allow: GET, HEAD, OPTIONS\r\n\r\n".getBytes(), formatter.headers(response));
    }
}
