package server.response;

import server.directory.DefaultDirectory;
import server.request.Handler;
import org.junit.Before;
import org.junit.Test;
import server.request.Request;
import stubs.server.RoutesStub;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SelectorTest {
    Map<String, Map<String, Handler>> routes;

    @Before
    public void setup() {
        routes = RoutesStub.ROUTES;
    }

    @Test
    public void shouldReturnOKResponse() {
        Request request = new Request("GET", "/test-route", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("200 OK", response.status());
        assertEquals("GET, HEAD", response.headers().get("Allow"));
        assertEquals("9", response.headers().get("Content-Length"));
        assertArrayEquals("test body".getBytes(), response.body());
    }

    @Test
    public void shouldReturnBadRequestResponseForInvalidMethod() {
        Request request = new Request("", "/test-route", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("400 Bad Request", response.status());
    }

    @Test
    public void shouldReturnBadRequestResponseForInvalidPath() {
        Request request = new Request("GET", "", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("400 Bad Request", response.status());
    }

    @Test
    public void shouldReturnInternalErrorWhenRoutingThrowsException() {
        Request request = new Request("GET", "/error-route", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("500 Internal Server Error", response.status());
    }

    @Test
    public void shouldReturnNotFoundResponse() {
        Request request = new Request("GET", "/not_found_resource", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("404 Not Found", response.status());
    }

    @Test
    public void shouldReturnNotAllowedResponse() {
        Request request = new Request("POST", "/test-route", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("405 Method Not Allowed", response.status());
    }

    @Test
    public void shouldReturnInternalServerErrorResponse() {
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(new IOException());

        assertEquals("500 Internal Server Error", response.status());
    }

    @Test
    public void shouldReturnResponseWithDirectory() {
        Request request = new Request("GET", "/", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);
        String expectedBody = "<h1>Index</h1>" +
            "<h3>public_stub</h3>" +
            "<ul>" +
            "<li><a href='/.DS_Store'>.DS_Store</a></li>" +
            "<li><a href='/foo.txt'>foo.txt</a></li>" +
            "<li><a href='/hello_world.html'>hello_world.html</a></li>" +
            "<li><a href='/more_stuff'>more_stuff</a></li>" +
            "<li><a href='/other_stuff'>other_stuff</a></li>" +
            "<li><a href='/so_rich.rtf'>so_rich.rtf</a></li>" +
            "</ul>";

        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
        assertEquals(expectedBody, new String(response.body()));
    }

    @Test
    public void shouldReturnResponseWithTextFileContentsInBody() {
        Request request = new Request("GET", "/other_stuff/orange/youglad/i_didnt.txt", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("say banana?", new String(response.body()));
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseGivenARoute() {
        Request request = new Request("GET", "/test-route", new HashMap<>(), "");
        Selector selector = new Selector(routes);
        Response response = selector.selectResponse(request);

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertArrayEquals("test body".getBytes(), response.body());
    }
}
