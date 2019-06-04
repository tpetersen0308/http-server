package server;

import app.App;
import org.junit.Before;
import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.ResponseBuilder;
import server.stubs.RoutesStub;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
    App app;

    @Before
    public void setupApp() {
        app = new App(RoutesStub.ROUTES);
    }

    @Test
    public void shouldReturnResponseWithOKStatus() {
        Request request = new Request("GET", "/simple_get", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request, app);

        assertEquals("200", response.statusCode());
        assertEquals("OK", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET", "/not_found_resource", new HashMap<String, String>(), "");
        Response response = ResponseBuilder.buildResponse(request, app);

        assertEquals("404", response.statusCode());
        assertEquals("Not Found", response.reasonPhrase());
    }

    @Test
    public void shouldReturnResponseWithHeaders() {
        Request request = new Request("POST", "/echo_body", new HashMap<String, String>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        Response response = ResponseBuilder.buildResponse(request, app);

        assertEquals("GET, OPTIONS, POST, HEAD", response.headers().get("Allow"));
        assertEquals("46", response.headers().get("Content-Length"));
    }

    @Test
    public void shouldReturnResponseWithBody() {
        Request request = new Request("POST", "/echo_body", new HashMap<String, String>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        Response response = ResponseBuilder.buildResponse(request, app);

        assertEquals("lorem ipsum dolor sit amet, adipiscing elit...", response.body());
    }
}
