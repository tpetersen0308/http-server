package server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;

public class RequestTest {
    @Test
    public void shouldReturnRequestPath() {
        HashMap<String, String> parsedRequestStub = new HashMap<>();
        parsedRequestStub.put("requestLine", "GET /simple_get HTTP/1.1");
        Request request = new Request(parsedRequestStub);

        assertEquals("/simple_get", request.getPath());
    }
}
