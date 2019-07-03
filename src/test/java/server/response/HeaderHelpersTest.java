package server.response;

import org.junit.Test;
import stubs.server.RoutesStub;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class HeaderHelpersTest {
    @Test
    public void shouldReturnAllowedMethods() {
        String allowed = HeaderHelpers.allowedMethods(RoutesStub.ROUTES.get("/test-route"));

        assertEquals("GET, HEAD", allowed);
    }

    @Test
    public void shouldReturnLocation() {
        String location = HeaderHelpers.location("test-host", "/test-route");

        assertEquals("http://test-host/test-route", location);
    }

    @Test
    public void shouldReturnContentType() {
        File file = new File("/test-file.txt");
        String contentType = HeaderHelpers.contentType(file);

        assertEquals("text/plain; charset=utf-8", contentType);
    }

    @Test
    public void shouldReturnTextTypeByDefault() {
        File file = new File("/test-file.foo");
        String contentType = HeaderHelpers.contentType(file);

        assertEquals("text/plain; charset=utf-8", contentType);
    }
}
