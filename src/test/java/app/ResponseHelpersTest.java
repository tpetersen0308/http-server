package app;

import app.support.ResponseHelpers;
import org.junit.Test;
import server.request.Request;
import server.response.Response;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ResponseHelpersTest {
    @Test
    public void shouldBuildA200OKResponse() {
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        Response response = ResponseHelpers.render(request, "some body");

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertEquals("some body", response.body());
    }

    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "127.0.0.1:5000");
        Request request = new Request("GET", "/redirect", headers, "");
        Response response = ResponseHelpers.redirectTo(request, "/simple_get");

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://127.0.0.1:5000/simple_get", response.headers().get("Location"));
    }

    @Test
    public void shouldBuildAResponseWithCustomHeaders() {
        Map<String, String> customHeaders = new HashMap<String, String>() {{ put("custom", "header");}};
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        Response response = ResponseHelpers.render(request, "", customHeaders);

        assertEquals("header", response.headers().get("custom"));
    }

    @Test
    public void shouldBuildAResponseWithDirectoryIndex() {
        Request request = new Request("GET", "/", new HashMap<>(), "");
        String directory = "./src/test/java/stubs/app/public_stub";
        String path = new File(directory).getAbsolutePath();
        String expectedBody = "<h1>Index</h1>" +
                "<h3>public_stub</h3>" +
                "<ul>" +
                "<li><a href='/foo.txt'>foo.txt</a></li>" +
                "<li><a href='/hello_world.html'>hello_world.html</a></li>" +
                "<li><a href='/more_stuff'>more_stuff</a></li>" +
                "<li><a href='/other_stuff'>other_stuff</a></li>" +
                "</ul>";

        Response response = ResponseHelpers.renderDirectory(request, path);

        assertEquals(expectedBody, response.body());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
    }
}
