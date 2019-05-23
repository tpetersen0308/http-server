package server;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import server.stubs.SocketStub;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class ClientTest {
    private Client client;
    private SocketStub socket;

    @Before
    public void setupClient() throws IOException {
        socket = new SocketStub("echo");
        client = new Client(socket);
    }

    @Test
    public void canRead() throws IOException {
        BufferedReader reader = client.getInputStreamReader();
        assertEquals("echo", reader.readLine());
    }

    @Test
    public void canWrite() throws IOException {
        PrintWriter writer = client.getOutputStreamWriter();
        writer.println("echo");
        assertEquals("echo\n", socket.getOutputStream().toString());
    }
}
