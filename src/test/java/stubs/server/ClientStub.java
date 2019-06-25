package stubs.server;

import server.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientStub extends Client {
    public ClientStub(Socket socket) {
        super(socket);
        setInputStreamReader(socket);
        setOutputStream(socket);
    }

    public String read() throws IOException {
        throw new IOException();
    }
}
