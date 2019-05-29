package server;

import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.ResponseBuilder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
    @Test
    public void shouldReturnResponseWithOKStatus() {
        Request request = new Request("GET /simple_get HTTP/1.1", "/simple_get", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("200", response.statusCode());
        assertEquals("OK", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET /not_found_resource HTTP/1.1", "/not_found_resource", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("404", response.statusCode());
        assertEquals("Not Found", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithHeaders() {
        Request request = new Request("OPTIONS /method_options HTTP/1.1", "/method_options", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("GET, HEAD, OPTIONS", response.headers().get("Allow"));
    }
}
