package app;

import app.support.DefaultDirectory;
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
            "<li><a href='/.DS_Store'>.DS_Store</a></li>" +
            "<li><a href='/foo.txt'>foo.txt</a></li>" +
            "<li><a href='/hello_world.html'>hello_world.html</a></li>" +
            "<li><a href='/more_stuff'>more_stuff</a></li>" +
            "<li><a href='/other_stuff'>other_stuff</a></li>" +
            "<li><a href='/so_rich.rtf'>so_rich.rtf</a></li>" +
            "</ul>";

        Response response = ResponseHelpers.renderDirectory(request, path);

        assertEquals(expectedBody, response.body());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithTextFileContents() {
        Request request = new Request("GET", "/other_stuff/orange/youglad/i_didnt.txt", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Response response = ResponseHelpers.renderTextFile(request, DefaultDirectory.PATH + request.path());

        assertEquals("say banana?", response.body());
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithHTMLFileContents() {
        Request request = new Request("GET", "/hello_world.html", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Response response = ResponseHelpers.renderTextFile(request, DefaultDirectory.PATH + request.path());

        assertEquals("<h1>Hello, World!</h1>", response.body());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithJSONFileContents() {
        Request request = new Request("GET", "/more_stuff/bird.json", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Response response = ResponseHelpers.renderTextFile(request, DefaultDirectory.PATH + request.path());
        String expectedJSON = "{\n" +
                "  \"name\": \"Cedar Waxwing\",\n" +
                "  \"scientific name\": \"Bombycilla Cedrorum\"\n" +
                "}";

        assertEquals(expectedJSON, response.body());
        assertEquals("application/json; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAPlainTextResponseByDefault() {
        Request request = new Request("GET", "/so_rich.rtf", new HashMap<>(), "");
        String path = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(path);
        Response response = ResponseHelpers.renderTextFile(request, DefaultDirectory.PATH + request.path());
        String expectedBody =  "{\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\\f0\\pardThis is some {\\b bold} text.\\par}";

        assertEquals(expectedBody, response.body());
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }
}
