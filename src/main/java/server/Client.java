package server;

import resources.Logger;

import java.io.OutputStream;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
    protected Socket socket;
    protected BufferedReader inputStreamReader;
    protected OutputStream outputStream;
    private Logger logger = new Logger();

    public Client(Socket socket) {
        this.socket = socket;
        setInputStreamReader(socket);
        setOutputStream(socket);
    }

    public String read() throws IOException {
        String content = in().readLine();

        return content;
    }

    public String read(int contentLength) throws IOException {
        char[] data = new char[contentLength];
        in().read(data, 0, contentLength);

        return new String(data);
    }

    public void write(byte[] data) {
        try {
            out().write(data);
        } catch (IOException err) {
            logger.log(err);
        }
    }

    public OutputStream out() {
        return outputStream;
    }

    public void closeSocket() {
        try {
            out().flush();
            socket.close();
        } catch (IOException err) {
            logger.log(err);
        }
    }

    protected BufferedReader in() {
        return inputStreamReader;
    }

    protected void setInputStreamReader(Socket socket) {
        try {
            inputStreamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException err) {
            logger.log(err);
        }
    }

    protected void setOutputStream(Socket socket) {
        try {
            outputStream = socket.getOutputStream();
        } catch (IOException err) {
            logger.log(err);
        }
    }
}
