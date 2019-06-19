package server;

import app.support.App;
import app.Routes;
import app.support.DefaultDirectory;
import org.junit.Before;
import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.ResponseSelector;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResponseSelectorTest {
    App app;

    @Before
    public void setupApp() {
        app = new App(Routes.ROUTES);
    }

    @Test
    public void shouldReturnResponseWithOKStatus() {
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("200 OK", response.status());
    }

    @Test
    public void shouldReturnResponseWithNotFoundStatusLine() {
        Request request = new Request("GET", "/not_found_resource", new HashMap<>(), "");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("404 Not Found", response.status());
    }

    @Test
    public void shouldReturnResponseWithHeaders() {
        Request request = new Request("POST", "/echo_body", new HashMap<>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("GET, HEAD, OPTIONS, POST", response.headers().get("Allow"));
        assertEquals("46", response.headers().get("Content-Length"));
    }

    @Test
    public void shouldReturnResponseWithBody() {
        Request request = new Request("POST", "/echo_body", new HashMap<>(), "lorem ipsum dolor sit amet, adipiscing elit...");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("lorem ipsum dolor sit amet, adipiscing elit...", response.body());
    }

    @Test
    public void shouldReturnResponseWithContentLengthHeaderForHeadRequest() {
        Request request = new Request("HEAD", "/get_with_body", new HashMap<>(), "");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("9", response.headers().get("Content-Length"));
    }

    @Test
    public void shouldReturnResponseWithNotAllowedStatusLine() {
        Request request = new Request("GET", "/get_with_body", new HashMap<>(), "");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("405 Method Not Allowed", response.status());
    }

    @Test
    public void shouldReturnMovedPermanentlyResponse() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "127.0.0.1:5000");
        Request request = new Request("GET", "/redirect", headers, "");
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://127.0.0.1:5000/simple_get", response.headers().get("Location"));
    }

    @Test
    public void shouldReturnResponseWithDirectory() {
        Request request = new Request("GET", "/", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();
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
        assertEquals(expectedBody, response.body());
    }

    @Test
    public void shouldReturnResponseWithTextFileContentsInBody() {
        Request request = new Request("GET", "/other_stuff/orange/youglad/i_didnt.txt", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        ResponseSelector responseSelector = new ResponseSelector(request, app);
        Response response = responseSelector.selectResponse();

        assertEquals("say banana?", response.body());
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }
}
