package server;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import server.stubs.SocketStub;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class ClientTest {
    @Test
    public void canRead() throws IOException {
        SocketStub socket = new SocketStub("echo");
        Client client = new Client(socket);
        BufferedReader reader = client.getInputStreamReader();
        assertEquals("echo", reader.readLine());
    }

    @Test
    public void canWrite() throws IOException {
        SocketStub socket = new SocketStub("echo");
        Client client = new Client(socket);

        PrintWriter writer = client.getOutputStreamWriter();
        writer.println("echo");
        assertEquals("echo\n", socket.getOutputStream().toString());
    }
}