package server;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import stubs.server.SocketStub;

public class ClientTest {
    @Test
    public void canRead() {
        SocketStub socket = new SocketStub("echo");
        Client client = new Client(socket);
        String actual = client.read();

        assertEquals("echo", actual);
    }

    @Test
    public void canReadBasedOnContentLength() {
        SocketStub socket = new SocketStub("echo");
        Client client = new Client(socket);
        String actual = client.read(4);

        assertEquals("echo", actual);
    }

    @Test
    public void canWrite() {
        SocketStub socket = new SocketStub("");
        Client client = new Client(socket);
        byte[] outputString = "echo\r\n".getBytes();
        client.write(outputString);

        assertEquals("echo\r\n", socket.getOutputStream().toString());
    }
}
