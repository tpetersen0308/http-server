package app;

import app.support.ResponseHandler;
import app.support.ResponseHelpers;
import org.junit.Test;
import server.request.Request;
import server.response.Response;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ResponseHelpersTest {
    @Test
    public void shouldBuildA200OKResponse() {
        ResponseHandler responseHandler = (Request request) -> ResponseHelpers.render(request, "some body");
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        Response response = responseHandler.dispatch(request);

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertEquals("some body", response.body());
    }

    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        ResponseHandler responseHandler = (Request request) -> ResponseHelpers.redirectTo(request, "/simple_get");
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "127.0.0.1:5000");
        Request request = new Request("GET", "/redirect", headers, "");

        Response response = responseHandler.dispatch(request);

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://127.0.0.1:5000/simple_get", response.headers().get("Location"));
    }

    @Test
    public void shouldBuildAResponseWithCustomHeaders() {
        Map<String, String> customHeaders = new HashMap<String, String>() {{ put("custom", "header");}};
        ResponseHandler responseHandler = (Request request) -> ResponseHelpers.renderWithHeaders(request,"", customHeaders);
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");

        Response response = responseHandler.dispatch(request);

        assertEquals("header", response.headers().get("custom"));
    }
}
