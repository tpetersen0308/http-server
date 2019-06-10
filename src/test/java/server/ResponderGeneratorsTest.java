package server;

import app.ActionDispatcher;
import app.Routes;
import org.junit.Test;
import server.request.Request;
import server.response.Response;
import server.response.Responder;
import server.response.ResponderGenerator;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ResponderGeneratorsTest {
    @Test
    public void shouldBuildA200OKResponse() {
        ActionDispatcher actionDispatcher = (Request request) -> "some body";
        Request request = new Request("GET", "/simple_get", new HashMap<>(), "");
        ResponderGenerator responderGenerator = new ResponderGenerator(Routes.ROUTES);
        Responder responder = responderGenerator.forGenericRequest(actionDispatcher);

        Response response = responder.getResponse(request);

        assertEquals("200 OK", response.status());
        assertEquals("9", response.headers().get("Content-Length"));
        assertEquals("some body", response.body());
    }

    @Test
    public void shouldBuildA301MovedPermanentlyResponse() {
        ActionDispatcher actionDispatcher = (Request request) -> "redirect: /simple_get";
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "127.0.0.1:5000");
        Request request = new Request("GET", "/redirect", headers, "");
        ResponderGenerator responderGenerator = new ResponderGenerator(Routes.ROUTES);
        Responder responder = responderGenerator.forGenericRequest(actionDispatcher);

        Response response = responder.getResponse(request);

        assertEquals("301 Moved Permanently", response.status());
        assertEquals("http://127.0.0.1:5000/simple_get", response.headers().get("Location"));
    }
}
