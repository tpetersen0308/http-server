package server;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import server.stubs.SocketStub;

import java.io.IOException;

public class HTTProtocolTest {

    @Test
    public void shouldReturnNotFound() throws IOException {
        SocketStub socket = new SocketStub("GET /not_found_resource HTTP/1.1");
        Client client = new Client(socket);
        HTTProtocol protocol = new HTTProtocol(client);
        protocol.run();
        assertEquals("HTTP/1.1 404 Not Found\r\n", socket.getOutputStream().toString());
    }
}
