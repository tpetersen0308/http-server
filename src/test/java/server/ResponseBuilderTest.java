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
        Request request = new Request("GET", "/simple_get", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("200", response.statusCode());
        assertEquals("OK", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET", "/not_found_resource", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("404", response.statusCode());
        assertEquals("Not Found", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithHeaders() {
        Request request = new Request("OPTIONS", "/method_options", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("GET, HEAD, OPTIONS", response.headers().get("Allow"));
    }

    @Test
    public void shouldReturnResponseWithBody() {
        Request request = new Request("POST", "/echo_body", new HashMap<String, String>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        Response response = ResponseBuilder.buildResponse(request);

        assertEquals("lorem ipsum dolor sit amet, adipiscing elit...", response.body());
    }
}
