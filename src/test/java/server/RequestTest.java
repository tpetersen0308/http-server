package server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class RequestTest {
    @Test
    public void shouldReturnRequestPath() {
        Map<String, String> emptyHeaders = new HashMap<>();
        Request request = new Request("GET /simple_get HTTP/1.1", emptyHeaders, "");

        assertEquals("/simple_get", request.getPath());
    }
}
