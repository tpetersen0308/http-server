package server;

import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.ResponseBuilder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
    @Test
    public void shouldReturnResponseWithOKStatusLine() {
        Request request = new Request("GET /simple_get HTTP/1.1", "/simple_get", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", response.statusLine());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET /not_found_resource HTTP/1.1", "/not_found_resource", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", response.statusLine());
    }
}
