package server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseTypesTest {
    @Test
    public void shouldBuildABasic200OKResponse() {
        Response response = ResponseTypes.ok(1);

        assertEquals("200 OK", response.status());
        assertEquals("1", response.headers().get("Content-Length"));
    }

    @Test
    public void shouldBuildA404NotFoundResponse() {
        Response response = ResponseTypes.notFound();

        assertEquals("404 Not Found", response.status());
    }

    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        Response response = ResponseTypes.movedPermanently("localhost", "/new-location");

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://localhost/new-location", response.headers().get("Location"));
    }

    @Test
    public void shouldBuildA405NotAllowedResponse() {
        Response response = ResponseTypes.methodNotAllowed("GET, HEAD");

        assertEquals("405 Method Not Allowed", response.status());
        assertEquals("GET, HEAD", response.headers().get("Allow"));
    }
}
