package server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TypesTest {
    @Test
    public void shouldBuildABasic200OKResponse() {
        Response response = Types.ok(1);

        assertEquals("200 OK", response.status());
        assertEquals("1", response.headers().get("Content-Length"));
        assertEquals("text/plain; charset=utf-8", response.headers().get("Content-Type"));

    }

    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        Response response = Types.movedPermanently("localhost", "/new-location");

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://localhost/new-location", response.headers().get("Location"));
    }

    @Test
    public void shouldBuildA400BadRequestResponse() {
        Response response = Types.badRequest();

        assertEquals("400 Bad Request", response.status());
    }

    @Test
    public void shouldBuildA404NotFoundResponse() {
        Response response = Types.notFound();

        assertEquals("404 Not Found", response.status());
    }

    @Test
    public void shouldBuildA405NotAllowedResponse() {
        Response response = Types.methodNotAllowed("GET, HEAD");

        assertEquals("405 Method Not Allowed", response.status());
        assertEquals("GET, HEAD", response.headers().get("Allow"));
    }

    @Test
    public void shouldBuildA500InternalServerErrorResponse() {
        Response response = Types.internalServerError();

        assertEquals("500 Internal Server Error", response.status());
    }
}
