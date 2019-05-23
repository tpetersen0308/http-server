package server.stubs;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SocketStub extends Socket {
    private OutputStream outputStream;
    private InputStream inputStream;
    private String inputStub;

    public SocketStub(String inputStub) {
        this.inputStub = inputStub;
        this.outputStream = new ByteArrayOutputStream();
        this.inputStream = new ByteArrayInputStream(inputStub.getBytes());
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }
}