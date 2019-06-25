package server.response;

import server.directory.DefaultDirectory;
import org.junit.Test;
import server.request.Request;
import stubs.server.RoutesStub;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MethodsTest {
    @Test
    public void shouldBuildABasicResponse() {
        Request request = new Request("HEAD", "/test-route", new HashMap<>(), "");
        Response response = Methods.render(request, "test body".getBytes());

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertArrayEquals("".getBytes(), response.body());
    }

    @Test
    public void shouldBuildAResponseWithBody() {
        Request request = new Request("GET", "/test-route", new HashMap<>(), "");
        Response response = Methods.render(request, "test body".getBytes());

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertArrayEquals("test body".getBytes(), response.body());
    }

    @Test
    public void shouldBuildAResponseGivenARoute() {
        Request request = new Request("GET", "/test-route", new HashMap<>(), "");
        Response response = Methods.fromRoute(request, RoutesStub.ROUTES.get("/test-route"));

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertArrayEquals("test body".getBytes(), response.body());
    }
    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "127.0.0.1:5000");
        Request request = new Request("GET", "/test-route", headers, "");
        Response response = Methods.redirectTo(request, "/new-path");

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://127.0.0.1:5000/new-path", response.headers().get("Location"));
    }

    @Test
    public void shouldBuildAResponseWithCustomHeaders() {
        Map<String, String> customHeaders = new HashMap<String, String>() {{ put("custom", "header");}};
        Request request = new Request("GET", "/test-route", new HashMap<>(), "");
        Response response = Methods.render(request, "".getBytes(), customHeaders);

        assertEquals("header", response.headers().get("custom"));
    }

    @Test
    public void shouldBuildAResponseWithDirectoryIndex() {
        Request request = new Request("GET", "/", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        DefaultDirectory.setPath(directoryPath);
        String path = new File(directoryPath).getAbsolutePath();
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

        Response response = Methods.renderDirectory(request, path);

        assertArrayEquals(expectedBody.getBytes(), response.body());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithTextFileContents() throws IOException {
        Request request = new Request("GET", "/other_stuff/orange/youglad/i_didnt.txt", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        Response response = Methods.renderFile(request, directoryPath + request.path());

        assertArrayEquals("say banana?".getBytes(), response.body());
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithHTMLFileContents() throws IOException {
        Request request = new Request("GET", "/hello_world.html", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        Response response = Methods.renderFile(request, directoryPath + request.path());

        assertArrayEquals("<h1>Hello, World!</h1>".getBytes(), response.body());
        assertEquals("text/html; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithJSONFileContents() throws IOException {
        Request request = new Request("GET", "/more_stuff/bird.json", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        Response response = Methods.renderFile(request, directoryPath + request.path());
        String expectedJSON = "{\n" +
                "  \"name\": \"Cedar Waxwing\",\n" +
                "  \"scientific name\": \"Bombycilla Cedrorum\"\n" +
                "}";

        assertArrayEquals(expectedJSON.getBytes(), response.body());
        assertEquals("application/json; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAResponseWithImageFileContents() throws IOException {
        Request request = new Request("GET", "/more_stuff/damn_good_coffee.jpg", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        Response response = Methods.renderFile(request, directoryPath + request.path());
        byte[] expected = Files.readAllBytes(Paths.get(directoryPath + request.path()));

        assertArrayEquals(expected, response.body());
        assertEquals("image/jpeg; charset=utf-8", response.headers().get("Content-Type"));
    }

    @Test
    public void shouldBuildAPlainTextResponseByDefault() throws IOException {
        Request request = new Request("GET", "/so_rich.rtf", new HashMap<>(), "");
        String directoryPath = "./src/test/java/stubs/app/public_stub";
        Response response = Methods.renderFile(request, directoryPath + request.path());
        String expectedBody =  "{\\rtf1\\ansi{\\fonttbl\\f0\\fswiss Helvetica;}\\f0\\pardThis is some {\\b bold} text.\\par}";

        assertArrayEquals(expectedBody.getBytes(), response.body());
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));
    }
}
