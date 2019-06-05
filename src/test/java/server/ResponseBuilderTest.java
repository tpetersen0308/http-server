package server;

import app.App;
import app.Routes;
import org.junit.Before;
import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.ResponseBuilder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class ResponseBuilderTest {
    App app;

    @Before
    public void setupApp() {
        app = new App(Routes.ROUTES);
    }

    @Test
    public void shouldReturnResponseWithOKStatus() {
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();

        assertEquals("200 OK", response.status());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET", "/not_found_resource", new HashMap<>(), "");
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();

        assertEquals("404 Not Found", response.status());
    }

    @Test
    public void shouldReturnResponseWithHeaders() {
        Request request = new Request("POST", "/echo_body", new HashMap<>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();

        assertEquals("GET, HEAD, OPTIONS, POST", response.headers().get("Allow"));
        assertEquals("46", response.headers().get("Content-Length"));
    }

    @Test
    public void shouldReturnResponseWithBody() {
        Request request = new Request("POST", "/echo_body", new HashMap<>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();

        assertEquals("lorem ipsum dolor sit amet, adipiscing elit...", response.body());
    }

    @Test
    public void shouldReturnResponseWithContentLengthHeaderForHeadRequest() {
        Request request = new Request("HEAD", "/get_with_body", new HashMap<>(), "");
        ResponseBuilder responseBuilder = new ResponseBuilder(request, app.routes().get(request.path()));
        Response response = responseBuilder.build();

        assertEquals("", response.body());
        assertEquals("9", response.headers().get("Content-Length"));
    }
}
